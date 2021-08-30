package com.apartcache.starter.manage;

import com.apartcache.starter.config.CacheServiceManager;
import com.apartcache.starter.config.ServiceManager;
import com.apartcache.starter.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheImpl implements CacheI {
    ServiceManager serviceManager;

    @Override
    public void add(String method, Integer second) {
        Method method1 = ClassUtils.toMethod(method);
        if(Optional.ofNullable(method1).isPresent()){
            serviceManager.add(method1, second);
        }
    }

    @Override
    public void remove(String method) {
        Method method1 = ClassUtils.toMethod(method);
        if(Optional.ofNullable(method1).isPresent()){
            serviceManager.remove(null, method1);
        }
    }

    @Override
    public void remove(Long id) {
        serviceManager.remove(id, null);
    }

    @Override
    public Integer size() {
        return serviceManager.size();
    }

    @Override
    public String getCacheName(String method) {
        Method method1 = ClassUtils.toMethod(method);
        if(Optional.ofNullable(method1).isPresent()){
            return serviceManager.getCacheName(method1);
        }
        return null;
    }

    @Override
    public String[] getAll() {
        return serviceManager.getAll();
    }

    public CacheImpl(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
}
