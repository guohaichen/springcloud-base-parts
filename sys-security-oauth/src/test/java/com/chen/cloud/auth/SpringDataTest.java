package com.chen.cloud.auth;

import com.chen.cloud.auth.entity.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
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
    RedisTemplate redisTemplate;

    @Test
    public void redisWrite() {
        redisTemplate.opsForValue().set("name", "chenguohai");
    }

    @Test
    public void getUser() {
        User user = (User) redisTemplate.opsForValue().get("login:user:15808213950");

        if (user != null)
            System.out.println(user);
    }
}
