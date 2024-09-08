package com.ryuneng.goldauth.global.exception.handler;

import com.ryuneng.goldauth.global.exception.CustomException;
import com.ryuneng.goldauth.global.exception.ErrorCode;
import com.ryuneng.goldauth.global.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomException
    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {

        log.error("handleCustomException", e);

        ErrorCode errorCode = e.getErrorCode();
        String message = e.getMessage();

        ErrorResponse response = ErrorResponse.of(
                errorCode.getHttpStatus(),
                message
        );

        return ResponseEntity.badRequest().body(response);
    }

    // Validation Exception 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleBindException(MethodArgumentNotValidException e) {

        log.error("MethodArgumentNotValidException : {}", e.getMessage());

        ErrorResponse response = ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage() // 첫번째 예외 메시지만 반환
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSqlException(SQLIntegrityConstraintViolationException e) {

        log.error("SQLIntegrityConstraintViolationException : {}", e.getMessage());

        ErrorResponse response = ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );

        return ResponseEntity.badRequest().body(response);
    }
}
