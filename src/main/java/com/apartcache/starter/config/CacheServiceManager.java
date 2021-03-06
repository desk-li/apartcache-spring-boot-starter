package com.apartcache.starter.config;


import com.apartcache.starter.bean.ApartCache;
import com.apartcache.starter.bean.CacheBean;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheServiceManager implements ServiceManager{

    final Map<String, CacheBean> cacheBeanMap = new ConcurrentHashMap<>();
    ApartCache apartCache;
    CacheNameGenerator cacheNameGenerator;

    @Override
    public void add(Method method, Integer second) {
        Class<?> declaringClass = method.getDeclaringClass();
        CacheBean cacheBean = cacheBeanMap.get(declaringClass.getName());
        CacheBean cacheBean1 = Optional.ofNullable(cacheBean).orElseGet(() -> new CacheBean());
        cacheBean1.setClaz(declaringClass);
        cacheBean1.getMethodSet().add(method);
        cacheBeanMap.put(declaringClass.getName(), cacheBean1);
    }

    @Override
    public void set(Long id, Integer second) {

    }

    @Override
    public void remove(Long id, Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        CacheBean cacheBean = cacheBeanMap.get(declaringClass.getName());
        CacheBean cacheBean1 = Optional.ofNullable(cacheBean).orElseGet(() -> new CacheBean());
        cacheBean1.getMethodSet().remove(method);
        cacheBeanMap.put(declaringClass.getName(), cacheBean1);
    }

    @Override
    public Integer size() {
        return cacheBeanMap.values().stream().map(CacheBean::getMethodSet).mapToInt(Set::size).sum();
    }

    @Override
    public String getCacheName(Method method) {
        return cacheNameGenerator.generate(method);
    }

    @Override
    public String[] getAll() {
        List<Set<Method>> collect = cacheBeanMap.values().stream().map(CacheBean::getMethodSet).collect(Collectors.toList());
        Set<Method> set = new HashSet<>();
        collect.forEach(temp ->{
            set.addAll(temp);
        });
        return set.stream().map(Method::toString).collect(Collectors.toList()).toArray(new String[set.size()]);
    }

    @Override
    public boolean contains(Method method){
        boolean b = false;
        Class<?> declaringClass = method.getDeclaringClass();
        Class<?>[] interfaces = declaringClass.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if(interfaces[i].getPackage().getName().contains(apartCache.getCachePackage())){
                CacheBean cacheBean = cacheBeanMap.get(interfaces[i].getName());
                if(Optional.ofNullable(cacheBean).isPresent()){
                    try {
                        Method method1 = interfaces[i].getMethod(method.getName(), method.getParameterTypes());
                        if(Optional.ofNullable(method1).isPresent()){
                            b =  cacheBean.getMethodSet().stream().anyMatch(m->{
                                return ObjectUtils.nullSafeEquals(m.getName(), method.getName())
                                        && ObjectUtils.nullSafeEquals(
                                                m.getParameterTypes(),
                                        method.getParameterTypes()
                                );
                            });
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return b;
    }

    public CacheServiceManager(ApartCache apartCache, CacheNameGenerator cacheNameGenerator) {
        this.apartCache = apartCache;
        this.cacheNameGenerator = cacheNameGenerator;
    }
}
