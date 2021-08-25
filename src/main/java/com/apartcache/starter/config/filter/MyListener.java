package com.apartcache.starter.config.filter;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Created by desk
 *
 * @date 2021/8/25
 */
public class MyListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("请求结束，销毁");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("请求开始，初始化");
    }
}
