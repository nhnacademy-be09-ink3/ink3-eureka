package com.example.waterwork.service.login.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("로그인 실패!");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
