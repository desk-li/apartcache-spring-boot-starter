package com.apartcache.starter.config.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 过滤器
 * Created by desk
 * @date 2021/8/25
 */
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter 过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter 过滤器工作");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("MyFilter 过滤器销毁");
    }
}
