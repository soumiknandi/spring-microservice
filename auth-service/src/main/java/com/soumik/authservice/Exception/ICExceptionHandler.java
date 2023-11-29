package com.soumik.authservice.Exception;

import com.soumik.authservice.InvalidCredentialException;
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

@RestControllerAdvice
public class ICExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Object> handleException(InvalidCredentialException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error",ex.getMessage());
        return new ResponseEntity<Object>(errorMap, HttpStatus.BAD_REQUEST);
    }

}
