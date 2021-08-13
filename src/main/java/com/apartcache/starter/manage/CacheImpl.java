package com.apartcache.starter.manage;

import com.apartcache.starter.config.CacheServiceManager;
import com.apartcache.starter.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheImpl implements CacheI {
    CacheServiceManager cacheServiceManager;

    @Override
    public void add(String method) {
        Method method1 = ClassUtils.toMethod(method);
        if(Optional.ofNullable(method1).isPresent()){
            cacheServiceManager.add(method1);
        }
    }

    @Override
    public void remove(String method) {
        Method method1 = ClassUtils.toMethod(method);
        if(Optional.ofNullable(method1).isPresent()){
            cacheServiceManager.remove(method1);
        }
    }

    @Override
    public Integer size() {
        return cacheServiceManager.size();
    }

    @Override
    public String getCacheName(String method) {
        Method method1 = ClassUtils.toMethod(method);
        if(Optional.ofNullable(method1).isPresent()){
            return cacheServiceManager.getCacheName(method1);
        }
        return "unknown method: "+method;
    }

    @Override
    public String[] getAll() {
        return cacheServiceManager.getAll();
    }

    public CacheImpl(CacheServiceManager cacheServiceManager) {
        this.cacheServiceManager = cacheServiceManager;
    }
}
