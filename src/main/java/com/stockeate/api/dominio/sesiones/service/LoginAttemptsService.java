package com.stockeate.api.dominio.sesiones.service;

import org.springframework.stereotype.Service;

import com.stockeate.api.parametros.ApiConstants;
import com.stockeate.api.redis.RedisKeys;
import com.stockeate.api.redis.RedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginAttemptsService {
    
    private final RedisService redisService;
    
    public void registrarIntento (String ip) {
        String key = RedisKeys.login_attempts + ip;
        Long intentos = redisService.increment(key, ApiConstants.TIEMPO_INTENTOS);
        if (intentos >= ApiConstants.MAX_INTENTOS_LOGIN)
            bloquearUsuario(ip);
    }

    public void limpiarIntentos (String ip) {
        String key = RedisKeys.login_attempts + ip;
        redisService.delete(key);
    }

    public void bloquearUsuario(String ip) {
        String key = RedisKeys.blocked_ips + ip;
        redisService.set(key, "block", ApiConstants.TIEMPO_BLOQUEO_IP);
    }

    public boolean estaBloqueada(String ip) {
        String key = RedisKeys.blocked_ips + ip;
        String block = redisService.get(key);
        return block != null && block.equals("block");
    }

}
