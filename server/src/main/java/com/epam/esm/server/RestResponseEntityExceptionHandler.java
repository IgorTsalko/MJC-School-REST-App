package com.epam.esm.server;

import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.exception.GiftCertificateException;
import com.epam.esm.server.entity.ExceptionResponse;
import org.springframework.beans.TypeMismatchException;
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
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GiftCertificateException.class)
    protected ResponseEntity<Object> handleGiftCertificateException(GiftCertificateException ex) {
        ErrorDefinition errorDefinition = ex.getErrorDefinition();
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(errorDefinition.getErrorCode())
                .setDetails(
                        String.format(errorDefinition.getErrorMessageTemplate(), ex.getEntityId()));
        return new ResponseEntity<>(exceptionResponse, errorDefinition.getHttpStatus());
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccess(DataAccessException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40901)
                .setDetails(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        String[] details = ex.getConstraintViolations()
                .stream()
                .map(constr -> constr.getInvalidValue() + " is invalid value. Id " + constr.getMessage())
                .collect(Collectors.toList())
                .toArray(String[]::new);

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40001)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String[] details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.toList())
                .toArray(String[]::new);

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40002)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40003)
                .setDetails(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String[] details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.toList())
                .toArray(String[]::new);

        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(40004)
                .setDetails(details);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
