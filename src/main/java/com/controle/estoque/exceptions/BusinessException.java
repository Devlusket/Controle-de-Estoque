package com.controle.estoque.exceptions;


public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}