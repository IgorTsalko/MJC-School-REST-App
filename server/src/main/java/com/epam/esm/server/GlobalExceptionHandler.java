package com.epam.esm.server;

import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.common.exception.CustomErrorCodes;
import com.epam.esm.common.exception.GiftCertificateException;
import com.epam.esm.server.entity.ExceptionResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String DATABASE_CONFLICT_MESSAGE = "database-conflict";
    private final static String BAD_REQUEST_MESSAGE = "bad-request";
    private final static String ACCESS_DENIED_MESSAGE = "access-denied";
    private final static String BAD_CREDENTIALS_MESSAGE = "bad-credentials";
    private final static String AUTHORIZATION_REQUIRED_MESSAGE = "authorization-required";
    private final static String DATABASE_EXCEPTION_MESSAGE = "database-exception";
    private final static String REQUEST_INCORRECT_VALUE_MESSAGE = "request.incorrect-value";
    private final static String REQUEST_INCORRECT_PARAM_MESSAGE = "request.incorrect-param";
    private final static String REQUEST_INCORRECT_BODY_MESSAGE = "request.incorrect-body";
    private final static String METHOD_NOT_SUPPORTED_MESSAGE = "method-not_supported";
    private final static String SERVER_ERROR_MESSAGE = "server-error";

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleThrowable(Throwable throwable, Locale locale) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.SERVER_ERROR)
                .setDetails(List.of(messageSource.getMessage(SERVER_ERROR_MESSAGE, null, locale)));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GiftCertificateException.class)
    protected ResponseEntity<Object> handleGiftCertificateException(GiftCertificateException ex, Locale locale) {
        ErrorDefinition errorDefinition = ex.getErrorDefinition();
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(errorDefinition.getErrorCode())
                .setDetails(List.of(messageSource.getMessage(
                        errorDefinition.getErrorMessageTemplate(),
                        new Object[] {ex.getData()},
                        locale))
                );
        return new ResponseEntity<>(exceptionResponse, errorDefinition.getHttpStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataAccess(DataIntegrityViolationException ex, Locale locale) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.DATABASE_CONFLICT)
                .setDetails(List.of(messageSource.getMessage(DATABASE_CONFLICT_MESSAGE, null, locale)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleDataAccess(AuthenticationException ex, Locale locale) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.BAD_CREDENTIALS)
                .setDetails(List.of(messageSource.getMessage(BAD_CREDENTIALS_MESSAGE, null, locale)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleDataAccess(AccessDeniedException ex, Locale locale, Authentication auth) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        HttpStatus httpStatus;

        if (auth == null) {
            exceptionResponse
                    .setErrorCode(CustomErrorCodes.AUTHORIZATION_REQUIRED)
                    .setDetails(List.of(messageSource.getMessage(AUTHORIZATION_REQUIRED_MESSAGE, null, locale)));
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            exceptionResponse
                    .setErrorCode(CustomErrorCodes.ACCESS_DENIED)
                    .setDetails(List.of(messageSource.getMessage(ACCESS_DENIED_MESSAGE, null, locale)));
            httpStatus = HttpStatus.FORBIDDEN;
        }

        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccess(DataAccessException ex, Locale locale) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.DATABASE_ERROR)
                .setDetails(List.of(messageSource.getMessage(DATABASE_EXCEPTION_MESSAGE, null, locale)));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    protected ResponseEntity<Object> handleDataAccess(InvalidDataAccessResourceUsageException ex, Locale locale) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.BAD_REQUEST)
                .setDetails(List.of(messageSource.getMessage(BAD_REQUEST_MESSAGE, null, locale)));
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
                .setErrorCode(CustomErrorCodes.CONSTRAINT_VIOLATION)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(c -> String.format("%s %s", c.getField(), c.getDefaultMessage()))
                .collect(Collectors.toList());

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.METHOD_ARGUMENT_NOT_VALID)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.REQUEST_INCORRECT_VALUE)
                .setDetails(List.of(messageSource.getMessage(
                        REQUEST_INCORRECT_VALUE_MESSAGE,
                        new Object[]{ex.getValue()},
                        LocaleContextHolder.getLocale()))
                );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(c -> messageSource.getMessage(
                        REQUEST_INCORRECT_PARAM_MESSAGE,
                        new Object[]{c.getRejectedValue(), c.getField()},
                        LocaleContextHolder.getLocale()))
                .collect(Collectors.toList());

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.REQUEST_INCORRECT_PARAM)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.REQUEST_INCORRECT_BODY)
                .setDetails(List.of(messageSource.getMessage(
                        REQUEST_INCORRECT_BODY_MESSAGE,
                        null,
                        LocaleContextHolder.getLocale()))
                );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(CustomErrorCodes.METHOD_NOT_SUPPORTED)
                .setDetails(List.of(messageSource.getMessage(
                        METHOD_NOT_SUPPORTED_MESSAGE,
                        null,
                        LocaleContextHolder.getLocale()))
                );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
