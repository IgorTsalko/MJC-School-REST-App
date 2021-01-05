package com.epam.esm.server;

import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.exception.GiftCertificateException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GiftCertificateException.class})
    protected ResponseEntity<Object> handle(GiftCertificateException ex, WebRequest request) {
        ErrorDefinition errorDefinition = ex.getErrorDefinition();
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(errorDefinition.getErrorCode())
                .setErrorMessage(String.format(errorDefinition.getErrorMessageTemplate(), ex.getParams()));
        return new ResponseEntity<>(exceptionResponse, errorDefinition.getHttpStatus());
    }

    @ExceptionHandler(value = {DataAccessException.class})
    protected ResponseEntity<Object> handle(DataAccessException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse()
                .setErrorCode(50001)
                .setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_GATEWAY);
    }
}
