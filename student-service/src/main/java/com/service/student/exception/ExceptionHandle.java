package com.service.student.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeExceptions(RuntimeException exception, WebRequest request){
        Map<String,Object> errorMap=new LinkedHashMap<>();

        errorMap.put("Message:", exception.getMessage());

        return new ResponseEntity<Map<String, Object>>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementExceptions(NoSuchElementException exception, WebRequest request){
        Map<String,Object> errorMap=new LinkedHashMap<>();

        errorMap.put("Message:", exception.getMessage()+ ". Please try with a different parameter.");

        return new ResponseEntity<Map<String, Object>>(errorMap, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,Object> errorMap=new LinkedHashMap<>();

        errorMap.put("Message:", "You are not using the correct Request Method.");

        return new ResponseEntity<Object>(errorMap, HttpStatus.BAD_REQUEST);
    }
}