package com.apartcache.starter.config.handler;

import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by desk
 *
 * @date 2021/8/9
 */
public class CacheProxy<T> implements InvocationHandler {

    RedisTemplate redisTemplate;
    T t;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
