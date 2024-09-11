package com.ryuneng.goldresource.global.exception.response;

import lombok.Getter;

@Getter
public class SuccessResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public SuccessResponse(boolean success, String message, T data) {

        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> SuccessResponse<T> ok(String message, T data) {

        return new SuccessResponse<>(true, message, data);
    }

}
