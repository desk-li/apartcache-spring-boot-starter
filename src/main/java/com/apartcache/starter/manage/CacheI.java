package com.apartcache.starter.manage;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public interface CacheI {

    void add(String method);

    void remove(String method);

    Integer size();

    String getCacheName(String method);

    String[] getAll();
}
