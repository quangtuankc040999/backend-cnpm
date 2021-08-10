package com.example.demo.exception;

public class SignUpException extends RuntimeException {
    public SignUpException(String errorMessage) {
        super(errorMessage);
    }
}