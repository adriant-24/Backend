package com.shop.onlineshop.exception;

public class JWTTokenException extends RuntimeException{

    public JWTTokenException(String message) {
        super(message);
    }

    public JWTTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
