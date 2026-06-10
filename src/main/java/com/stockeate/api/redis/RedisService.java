package com.stockeate.api.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.*;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value, long ttlMinutos) {
        redisTemplate.opsForValue().set(key, value, ttlMinutos, TimeUnit.MINUTES);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long increment (String key, long ttlMinutos) {
        Long intentos = redisTemplate.opsForValue().increment(key);
        if (intentos == 1)
            redisTemplate.expire(key, ttlMinutos, TimeUnit.MINUTES);
        return intentos;
    }

    /** Lee y borra la clave en una sola operación atómica (GETDEL). */
    public String getAndDelete(String key) {
        return redisTemplate.opsForValue().getAndDelete(key);
    }

}