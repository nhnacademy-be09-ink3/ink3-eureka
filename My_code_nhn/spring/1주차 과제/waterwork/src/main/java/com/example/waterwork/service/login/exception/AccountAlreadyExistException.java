package com.example.waterwork.service.login.exception;

public class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException() {
        super("이미 계정이 존재합니다.");
    }

    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
