package com.challenge.portfolio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidAmountsException.class)
    public ResponseEntity<Object> handleInvalidAmountsException(
            InvalidAmountsException ex, WebRequest request) {
        return new ResponseEntity<>(getBody("Not valid Amounts"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RiskNotFoundException.class)
    public ResponseEntity<Object> handleInvalidAmountsException(
            RiskNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(getBody("Risk Not Found"), HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> getBody(String message){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);
        return body;
    }

}
