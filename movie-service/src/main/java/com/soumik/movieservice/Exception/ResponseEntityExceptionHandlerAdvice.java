package com.soumik.movieservice.Exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ResponseEntityExceptionHandlerAdvice extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : ex.getFieldErrors()) {
            errorMap.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errorMap, headers, status);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Object> handleException(MovieNotFoundException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error",ex.getMessage());
        return new ResponseEntity<Object>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Object> handleInvalidIdException(InvalidIdException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error",ex.getMessage());
        return new ResponseEntity<Object>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
