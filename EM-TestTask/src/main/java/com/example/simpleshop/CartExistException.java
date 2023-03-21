package com.example.simpleshop;

public class CartExistException extends RuntimeException{

    public CartExistException(String message) {
        super(message);
    }
}
