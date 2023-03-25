package com.example.simpleshop.exception;

public class CartExistException extends RuntimeException{

    public CartExistException(String message) {
        super(message);
    }
}
