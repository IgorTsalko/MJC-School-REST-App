package com.epam.esm.server;

import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.exception.GiftCertificateException;
import com.epam.esm.server.entity.ExceptionResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public RestResponseEntityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(GiftCertificateException.class)
    protected ResponseEntity<Object> handleGiftCertificateException(GiftCertificateException ex, Locale locale) {
        ErrorDefinition errorDefinition = ex.getErrorDefinition();
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(errorDefinition.getErrorCode())
                .setDetails(List.of(String.format(
                                messageSource.getMessage(errorDefinition.getErrorMessageTemplate(), null, locale),
                                ex.getEntityId())
                ));
        return new ResponseEntity<>(exceptionResponse, errorDefinition.getHttpStatus());
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccess(DataAccessException ex, Locale locale) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40001)
                .setDetails(List.of(messageSource.getMessage("database-exception", null, locale)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> details = ex.getConstraintViolations().stream()
                .map(v -> String.format("%s '%s' %s", StreamSupport
                        .stream(v.getPropertyPath().spliterator(), false)
                        .reduce((first, second) -> second).orElse(null), v.getInvalidValue(), v.getMessage()))
                .collect(Collectors.toList());

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40002)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleThrowable(Throwable throwable, Locale locale) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(50001)
                .setDetails(List.of(messageSource.getMessage("server-error", null, locale)));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(c -> String.format("%s %s", c.getField(), c.getDefaultMessage()))
                .collect(Collectors.toList());

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40003)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40004)
                .setDetails(List.of(String.format("%s %s", ex.getValue(), messageSource.getMessage(
                        "request.incorrect-value",
                        null,
                        LocaleContextHolder.getLocale())))
                );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(c -> String.format(messageSource.getMessage(
                        "request.incorrect-param",
                        null,
                        LocaleContextHolder.getLocale()), c.getRejectedValue(), c.getField()))
                .collect(Collectors.toList());

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40005)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
