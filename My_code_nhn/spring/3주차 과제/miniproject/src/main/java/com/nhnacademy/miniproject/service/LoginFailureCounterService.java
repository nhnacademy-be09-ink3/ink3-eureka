package com.nhnacademy.miniproject.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginFailureCounterService {
    private final String HASH_NAME = "LOGIN_FAIL_COUNT:";
    private final Duration TTL = Duration.ofMinutes(10);

    private final RedisTemplate<String, Object> redisTemplate;

    public LoginFailureCounterService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public int increase(String id){
        int cnt = 0;

        Object o = redisTemplate.opsForValue().get(HASH_NAME + id);
        if(o==null){
            redisTemplate.opsForValue().set(HASH_NAME + id, 1,  TTL);
            cnt = 1;
        }else{
            cnt = (int) o + 1;
            redisTemplate.opsForValue().set(HASH_NAME+id, cnt, TTL);
        }

        return cnt;
    }

    public void reset(String id){
        redisTemplate.delete(HASH_NAME + id);
    }

    public boolean check5count(String id){
        Object o = redisTemplate.opsForValue().get(HASH_NAME + id);
        if(o==null){
            return false;
        }
        int cnt = (int) o;
        return cnt >= 5;
    }
}
