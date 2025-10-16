package com.threego.algomemberservice.auth.command.application.service;

public interface RedisService {
    String getData(String key);
    void  setData(String key, String value);
    void  setDataExpire(String key, String value, long duration);
    boolean checkData(String key, String value);
    void  delData(String key);
}
