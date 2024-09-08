package com.ryuneng.goldauth.global.exception.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse<T> {

    private HttpStatus success;
    private String message;
    private T data;

    public SuccessResponse(HttpStatus success, String message, T data) {

        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> SuccessResponse<T> ok(String message, T data) {

        return new SuccessResponse<>(HttpStatus.OK, message, data);
    }

    public static <T> SuccessResponse<T> created(String message, T data) {

        return new SuccessResponse<>(HttpStatus.CREATED, message, data);
    }

}
