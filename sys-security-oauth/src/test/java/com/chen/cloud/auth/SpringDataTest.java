package com.chen.cloud.auth;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author cgh
 * @create 2023-08-28
 */
@SpringBootTest
public class SpringDataTest {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void redisWrite(){
        redisTemplate.opsForValue().set("name","chenguohai");
    }
}
