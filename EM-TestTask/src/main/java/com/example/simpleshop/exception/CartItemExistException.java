package com.example.simpleshop.exception;

public class CartItemExistException extends RuntimeException {

    public CartItemExistException(String message) {
        super(message);
    }
}
