package com.ithema.bigevent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate redisTemplate;
    @Test
    public void test1(){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("name","zhangsan",8, TimeUnit.SECONDS);
        ops.set("age","18",30, TimeUnit.SECONDS);
    }
    @Test
    public void test2(){
        System.out.println(redisTemplate.opsForValue().get("age"));
    }
}
