package com.apartcache.starter.manage;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public interface CacheI {

    void add(String method, Integer second);

    void remove(String method);

    void remove(Long id);

    Integer size();

    String getCacheName(String method);

    String[] getAll();


}
