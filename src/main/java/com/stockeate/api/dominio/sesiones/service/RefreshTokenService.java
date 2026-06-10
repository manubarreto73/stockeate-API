package com.stockeate.api.dominio.sesiones.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stockeate.api.exceptions.exceptions.BusinessException;
import com.stockeate.api.redis.RedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final String PREFIX = "refresh:";

    @Value("${security.jwt.refresh-expiration-days:7}")
    private int refreshExpirationDays;

    private final RedisService redisService;

    public String create(String email) {
        String token = UUID.randomUUID().toString();
        long ttlMinutes = (long) refreshExpirationDays * 24 * 60;
        redisService.set(PREFIX + token, email, ttlMinutes);
        return token;
    }

    /** Valida y revoca atómicamente el token actual, y emite uno nuevo (rotación). Devuelve el email. */
    public String validateAndRotate(String token, String newToken) {
        String email = redisService.getAndDelete(PREFIX + token);
        if (email == null)
            throw new BusinessException("Refresh token inválido o expirado");
        long ttlMinutes = (long) refreshExpirationDays * 24 * 60;
        redisService.set(PREFIX + newToken, email, ttlMinutes);
        return email;
    }

    public void revoke(String token) {
        redisService.delete(PREFIX + token);
    }
}
