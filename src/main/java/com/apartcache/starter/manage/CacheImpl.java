package com.apartcache.starter.manage;

import com.apartcache.starter.config.CacheServiceManager;

import java.lang.reflect.Method;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheImpl implements CacheI {
    CacheServiceManager cacheServiceManager;

    @Override
    public void add(String method) {
        String substring = method.substring(0, method.indexOf("("));
        String substring1 = substring.substring(0, substring.lastIndexOf("."));
        String substring2 = substring.substring(substring.lastIndexOf(".") + 1);
        try {
            Class<?> aClass = Class.forName(substring1);
            Method method1 = aClass.getMethod(substring2);
            cacheServiceManager.add(method1);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(String method) {
        String substring = method.substring(0, method.indexOf("("));
        String substring1 = substring.substring(0, substring.lastIndexOf("."));
        String substring2 = substring.substring(substring.lastIndexOf(".") + 1);
        try {
            Class<?> aClass = Class.forName(substring1);
            Method method1 = aClass.getMethod(substring2);
            cacheServiceManager.remove(method1);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer size() {
        return cacheServiceManager.size();
    }

    @Override
    public String getCacheName(String method) {
        String substring = method.substring(0, method.indexOf("("));
        String substring1 = substring.substring(0, substring.lastIndexOf("."));
        String substring2 = substring.substring(substring.lastIndexOf(".") + 1);
        try {
            Class<?> aClass = Class.forName(substring1);
            Method method1 = aClass.getMethod(substring2);
            return cacheServiceManager.getCacheName(method1);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String[] getAll() {
        return cacheServiceManager.getAll();
    }

    public CacheImpl(CacheServiceManager cacheServiceManager) {
        this.cacheServiceManager = cacheServiceManager;
    }
}
