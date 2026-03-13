package com.controle.estoque.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.controle.estoque.dtos.response.ErrorResponse;
import com.controle.estoque.exceptions.BusinessException;
import com.controle.estoque.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {


  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException err) {
    ErrorResponse errorResponse = new ErrorResponse(400, err.getMessage());
    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception err) {
    ErrorResponse errorResponse = new ErrorResponse(500, "Ocorreu um erro interno no servidor.");
    return ResponseEntity.internalServerError().body(errorResponse);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException err) {
    ErrorResponse errorResponse = new ErrorResponse(404, err.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException err) {
    ErrorResponse errorResponse = new ErrorResponse(400, err.getMessage());
    return ResponseEntity.badRequest().body(errorResponse);
  }

}
