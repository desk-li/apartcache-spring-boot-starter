package com.apartcache.starter.bean;

/**
 * Created by desk
 *
 * @date 2021/8/23
 */
public class SearchCondition {

    String clazz;
    String method;
    CacheData.LocalStatus status;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public CacheData.LocalStatus getStatus() {
        return status;
    }

    public void setStatus(CacheData.LocalStatus status) {
        this.status = status;
    }
}
