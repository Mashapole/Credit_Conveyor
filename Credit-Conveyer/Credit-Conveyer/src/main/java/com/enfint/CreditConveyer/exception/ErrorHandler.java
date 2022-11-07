package com.enfint.CreditConveyer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?>  exception(Exception ex, WebRequest wb)
    {
        ErrorModel errorModel= new ErrorModel(new Date(),ex.getMessage(),wb.getDescription(false),HttpStatus.NOT_FOUND);

        return new ResponseEntity(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
