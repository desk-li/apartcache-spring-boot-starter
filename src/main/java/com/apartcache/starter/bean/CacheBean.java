package com.apartcache.starter.bean;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheBean {
    Class claz;
    Set<Method> methodSet;

    public CacheBean() {
        methodSet = new CopyOnWriteArraySet();
    }


    public Class getClaz() {
        return claz;
    }

    public void setClaz(Class claz) {
        this.claz = claz;
    }

    public Set<Method> getMethodSet() {
        return methodSet;
    }

    public void setMethodSet(Set<Method> methodSet) {
        this.methodSet = methodSet;
    }
}
