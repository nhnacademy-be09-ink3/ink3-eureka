package com.example.waterwork.service.search.exception;

public class SearchFailException extends RuntimeException{
    public SearchFailException() {
        super("검색에 실패하였습니다.");
    }

    public SearchFailException(String message) {
        super(message);
    }
}
