package com.ryuneng.goldauth.global.exception.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private boolean success;
    private HttpStatus status;
    private String message;

    public ErrorResponse(boolean success, HttpStatus status, String message) {

        this.success = success;
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(HttpStatus status, String message) {

        return new ErrorResponse(false, status, message);
    }

}
