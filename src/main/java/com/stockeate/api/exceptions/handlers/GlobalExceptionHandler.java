package com.stockeate.api.exceptions.handlers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.stockeate.api.exceptions.exceptions.BusinessException;
import com.stockeate.api.exceptions.exceptions.ResourceNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ErrorResponse(401, "Credenciales inválidas", LocalDateTime.now())
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ErrorResponse(401, "Token expirado", LocalDateTime.now())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException  e) {
        
        Map<String, String> errores = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {errores.put(error.getField(), error.getDefaultMessage());});
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ErrorResponse(400, errores.toString(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ErrorResponse(400, e.getMessage(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ErrorResponse(404, e.getMessage(), LocalDateTime.now())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            new ErrorResponse(500, e.getMessage(), LocalDateTime.now())
        );
    }
}