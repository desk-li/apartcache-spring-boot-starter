package com.apartcache.starter.support.controller;

import com.apartcache.starter.bean.RequestCacheBean;
import com.apartcache.starter.manage.CacheI;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by desk
 *
 * @date 2021/8/26
 */
public class CacheController extends MyController{

    CacheI cacheI;

    @PostMapping(value = "/cache/add")
    @ResponseBody
    public Object add(@RequestBody RequestCacheBean requestCacheBean){
        cacheI.add(requestCacheBean.getMethod(), requestCacheBean.getSecond());
        return "success";
    }


    @DeleteMapping(value = "/cache/remove/{id}")
    @ResponseBody
    public Object remove(@PathVariable Long id) {
        cacheI.remove(id);
        return "success";
    }

    @GetMapping(value = "/cache/size")
    @ResponseBody
    public Object size() {
        return cacheI.size();
    }

    @PostMapping(value = "/cache/cacheName")
    @ResponseBody
    public Object cacheName(@RequestBody RequestCacheBean requestCacheBean) throws IOException {
        String method = requestCacheBean.getMethod();
        return cacheI.getCacheName(method);
    }

    @GetMapping(value = "/cache/all")
    @ResponseBody
    public Object all() {
        return cacheI.getAll();
    }


    public CacheController(CacheI cacheI) {
        this.cacheI = cacheI;
    }
}
