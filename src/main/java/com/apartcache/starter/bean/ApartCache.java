package com.apartcache.starter.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
@ConfigurationProperties(prefix = "apartcache")
public class ApartCache {
    private String cacheExpression;
    private String cachePackage;

    public String getCacheExpression() {
        return cacheExpression;
    }

    public void setCacheExpression(String cacheExpression) {
        this.cacheExpression = cacheExpression;
    }

    public String getCachePackage() {
        return cachePackage;
    }

    public void setCachePackage(String cachePackage) {
        this.cachePackage = cachePackage;
    }
}
