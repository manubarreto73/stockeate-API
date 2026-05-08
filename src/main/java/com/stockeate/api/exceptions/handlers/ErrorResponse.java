package com.stockeate.api.exceptions.handlers;

import java.time.LocalDateTime;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ErrorResponse {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;
}