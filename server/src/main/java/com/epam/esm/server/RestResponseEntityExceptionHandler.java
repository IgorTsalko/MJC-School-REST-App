package com.epam.esm.server;

import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.exception.GiftCertificateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GiftCertificateException.class})
    protected ResponseEntity<Object> handle(GiftCertificateException ex) {
        // errorDefinition here is null and I get NullPointerException on the line 20
        // Spring is wired GiftCertificateException via new constructor. ErrorDefinition and params are empty
        ErrorDefinition errorDefinition = ex.getErrorDefinition();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setErrorCode(errorDefinition.getErrorCode());
        exceptionResponse.setErrorMessage(String.format(errorDefinition.getErrorMessage(), ex.getParams()));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.valueOf(errorDefinition.name()));
    }
}
