package com.apartcache.starter.bean;

/**
 * Created by desk
 *
 * @date 2021/8/12
 */
public class ParamBean {
    Class paramClass;
    String paramName;

    public Class getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class paramClass) {
        this.paramClass = paramClass;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public ParamBean(Class paramClass, String paramName) {
        this.paramClass = paramClass;
        this.paramName = paramName;
    }
}
