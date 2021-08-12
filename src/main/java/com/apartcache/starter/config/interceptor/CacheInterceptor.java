package com.apartcache.starter.config.interceptor;

import com.apartcache.starter.bean.ApartCache;
import com.apartcache.starter.config.redis.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheInterceptor implements HandlerInterceptor {

    @Autowired
    RedisCacheManager redisCacheManager;

    @Autowired
    ApartCache apartCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
