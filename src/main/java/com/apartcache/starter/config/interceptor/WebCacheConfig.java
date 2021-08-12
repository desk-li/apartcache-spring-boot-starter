package com.apartcache.starter.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class WebCacheConfig implements WebMvcConfigurer {

    @Autowired
    private CacheInterceptor cacheInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(cacheInterceptor).addPathPatterns("/**");
    }
}
