package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.dto.ErrorResponseObj;
import com.adcash.productcatalog.exceptions.*;
import com.turingapp.myapp.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController{

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> authException(AuthenticationException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }

    @ExceptionHandler(value = PaginationException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> pagException(PaginationException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> pagException(UserException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }

    @ExceptionHandler(value = CategoryException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> catException(CategoryException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }

    @ExceptionHandler(value = DepartmentException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> deptException(DepartmentException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }
}
