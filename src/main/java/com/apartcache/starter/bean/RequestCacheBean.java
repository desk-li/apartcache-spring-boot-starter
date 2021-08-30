package com.apartcache.starter.bean;

import java.io.Serializable;

/**
 * Created by desk
 *
 * @date 2021/8/26
 */
public class RequestCacheBean implements Serializable {
    private static final long serialVersionUID = 8784791719163660044L;
    String method;
    Integer second;


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
}
