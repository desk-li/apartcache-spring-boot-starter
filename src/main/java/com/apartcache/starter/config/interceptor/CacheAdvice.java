package com.apartcache.starter.config.interceptor;

import com.apartcache.starter.config.redis.RedisCacheManager;
import com.apartcache.starter.util.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheAdvice implements MethodInterceptor {

    RedisCacheManager redisCacheManager;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object[] args = methodInvocation.getArguments();
        Object o = redisCacheManager.readCache(method, args);
        if(Optional.ofNullable(o).isPresent()){
            return o;
        }
        Object result = methodInvocation.proceed();
        if(Optional.ofNullable(result).isPresent()){
            redisCacheManager.writeCache(method, args, result);
        }
        return result;
    }

    public CacheAdvice(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }
}
