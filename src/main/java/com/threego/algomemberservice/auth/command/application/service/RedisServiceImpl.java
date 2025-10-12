package com.threego.algomemberservice.auth.command.application.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisServiceImpl implements RedisService{
    private final StringRedisTemplate stringRedisTemplate;

    public RedisServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void setData(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    @Override
    public boolean checkData(String key, String value) {
        String data = getData(key);

        if(data != null && data.equals(value)){
            delData(key);
            return true;
        }
        return false;
    }

    @Override
    public void delData(String key) {
        stringRedisTemplate.delete(key);
    }
}
