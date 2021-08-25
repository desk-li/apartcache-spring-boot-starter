package com.apartcache.starter.config.redis;


import com.apartcache.starter.config.CacheNameGenerator;
import com.apartcache.starter.config.ServiceManager;

import java.lang.reflect.Method;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class RedisCacheManager {

    RedisClient redisClient;

    ServiceManager serviceManager;

    CacheNameGenerator cacheNameGenerator;

    public Object readCache(Method method, Object[] args){
        if(serviceManager.contains(method)){
            System.out.println(method.getName()+": 读取缓存的数据");
            return doRead(method, args);
        }
        return null;
    }


    public void writeCache(Method method, Object[] args, Object object){
        if(serviceManager.contains(method)) {
            System.out.println(method.getName() + ": 向缓存写入数据");
            redisClient.set(cacheNameGenerator.generateKey(method, args), object);
        }
    }


    private Object doRead(Method method, Object[] args){
//        return "我已经说了 hello world !";
        return redisClient.get(cacheNameGenerator.generateKey(method, args));
    }


    public RedisCacheManager(RedisClient redisClient, ServiceManager serviceManager, CacheNameGenerator cacheNameGenerator) {
        this.redisClient = redisClient;
        this.serviceManager = serviceManager;
        this.cacheNameGenerator = cacheNameGenerator;
    }
}
