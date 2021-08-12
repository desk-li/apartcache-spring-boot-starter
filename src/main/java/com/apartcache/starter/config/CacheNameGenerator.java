package com.apartcache.starter.config;


import com.apartcache.starter.bean.CacheBean;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheNameGenerator {

    public String generate(Method method){
        String s = method.toString();
        s = s.substring(s.lastIndexOf(" "));
        return method.getDeclaringClass().getSimpleName();
    }


    public String generateKey(Method method, Object[] args){
        StringBuffer stringBuffer = new StringBuffer(generate(method)+":");
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if(i == parameters.length-1){
                stringBuffer.append(parameters[i].getName()+":"+args[i].hashCode());
            }else{
                stringBuffer.append(parameters[i].getName()+":"+args[i].hashCode()).append(":");
            }
        }
        return stringBuffer.toString();
    }


    public static void main(String[] args) {
        CacheBean cacheBean = new CacheBean();
        cacheBean.setClaz(List.class);
        System.out.println(cacheBean.hashCode());
        CacheBean cacheBean2 = new CacheBean();
        cacheBean2.setClaz(Map.class);
        System.out.println(cacheBean2.hashCode());
    }
}
