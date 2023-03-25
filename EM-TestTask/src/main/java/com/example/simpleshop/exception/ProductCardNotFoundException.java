package com.example.simpleshop.exception;

public class ProductCardNotFoundException extends RuntimeException {
    public ProductCardNotFoundException(String message) {
        super(message);
    }
}
