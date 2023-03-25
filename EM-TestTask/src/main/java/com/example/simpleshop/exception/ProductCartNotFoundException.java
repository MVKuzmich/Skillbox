package com.example.simpleshop.exception;

public class ProductCartNotFoundException extends RuntimeException {
    public ProductCartNotFoundException(String message) {
        super(message);
    }
}
