package com.apartcache.starter.config;


import java.lang.reflect.Method;

/**
 * Created by desk
 *
 * @date 2021/8/23
 */
public interface ServiceManager {
    void add(Method method);

    void remove(Long id, Method method);

    Integer size();

    String[] getAll();

    boolean contains(Method method);

    String getCacheName(Method method);
}
