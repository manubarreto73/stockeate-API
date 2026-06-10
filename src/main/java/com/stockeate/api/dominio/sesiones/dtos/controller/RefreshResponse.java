package com.stockeate.api.dominio.sesiones.dtos.controller;

import lombok.*;

@Getter
@AllArgsConstructor
public class RefreshResponse {

    private String accessToken;
    private String refreshToken;
}
