package com.apartcache.starter.config.redis;


import com.apartcache.starter.config.CacheNameGenerator;
import com.apartcache.starter.config.CacheServiceManager;

import java.lang.reflect.Method;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class RedisCacheManager {

    RedisClient redisClient;

    CacheServiceManager cacheServiceManager;

    CacheNameGenerator cacheNameGenerator;

    public Object searchCache(Method method, Object[] args){
        if(cacheServiceManager.contains(method)){
            return doSearch(method, args);
        }
        return null;
    }


    private Object doSearch(Method method, Object[] args){
        return "我已经说了 hello world !";
//        return redisClient.get(cacheNameGenerator.generateKey(method, args));
    }


    public RedisCacheManager(RedisClient redisClient, CacheServiceManager cacheServiceManager, CacheNameGenerator cacheNameGenerator) {
        this.redisClient = redisClient;
        this.cacheServiceManager = cacheServiceManager;
        this.cacheNameGenerator = cacheNameGenerator;
    }
}
