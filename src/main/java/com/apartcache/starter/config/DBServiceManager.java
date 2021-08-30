package com.apartcache.starter.config;


import com.alibaba.fastjson.JSONObject;
import com.apartcache.starter.bean.ApartCache;
import com.apartcache.starter.bean.CacheBean;
import com.apartcache.starter.bean.CacheData;
import com.apartcache.starter.bean.SearchCondition;
import com.apartcache.starter.mapper.CacheDataMapper;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class DBServiceManager implements ServiceManager {

    CacheDataMapper cacheDataMapper;
    ApartCache apartCache;
    CacheNameGenerator cacheNameGenerator;

    @Override
    public void add(Method method, Integer second) {
        Class<?> declaringClass = method.getDeclaringClass();
        String name = declaringClass.getName();
        String method1 = method.getName();
        Parameter[] parameters = method.getParameters();
        List<String> collect = Arrays.asList(parameters).stream().map(Parameter::getType).map(Class::getName).collect(Collectors.toList());
        String params = JSONObject.toJSONString(collect);
        CacheData cacheData = new CacheData();
        cacheData.setCacheName(cacheNameGenerator.generate(method));
        cacheData.setClazz(name);
        cacheData.setMethod(method1);
        cacheData.setParams(params);
        cacheData.setStatus(CacheData.LocalStatus.UNACTIVE);
        cacheData.setSecond(second);
        cacheDataMapper.add(cacheData);
    }

    @Override
    public void set(Long id, Integer second) {
        CacheData cacheData = new CacheData();
        cacheData.setId(id);
        cacheData.setSecond(second);
        cacheDataMapper.update(cacheData);
    }

    @Override
    public void remove(Long id, Method method) {
        CacheData cacheData = new CacheData();
        cacheData.setId(id);
        cacheData.setStatus(CacheData.LocalStatus.DELETED);
        cacheDataMapper.update(cacheData);
    }
    @Override
    public Integer size() {
        return cacheDataMapper.count();
    }

    @Override
    public String getCacheName(Method method) {
        return cacheNameGenerator.generate(method);
    }

    @Override
    public String[] getAll() {
        return cacheDataMapper.selectAll().toArray(new String[]{});
    }


    public boolean contains(Method method){
        SearchCondition condition = new SearchCondition();
        condition.setClazz(method.getDeclaringClass().getName());
        condition.setMethod(method.getName());
        List<CacheData> cacheData = cacheDataMapper.selectPage(condition, 0, 1);
        if(cacheData.size() == 1){
            return true;
        }
        return false;
    }

    public DBServiceManager(CacheDataMapper cacheDataMapper, ApartCache apartCache, CacheNameGenerator cacheNameGenerator) {
        this.cacheDataMapper = cacheDataMapper;
        this.apartCache = apartCache;
        this.cacheNameGenerator = cacheNameGenerator;
    }
}
