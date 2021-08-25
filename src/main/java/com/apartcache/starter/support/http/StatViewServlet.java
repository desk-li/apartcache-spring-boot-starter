package com.apartcache.starter.support.http;

import com.alibaba.fastjson.JSONObject;
import com.apartcache.starter.manage.CacheI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by desk
 *
 * @date 2021/8/12
 */
public class StatViewServlet extends HttpServlet {

    CacheI cacheI;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String mapping = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        switch (mapping){
            case "add":
                add(req, resp);
                break;
            case "remove":
                remove(req, resp);
                break;
            case "size":
                size(req, resp);
                break;
            case "cacheName":
                cacheName(req, resp);
                break;
            case "all":
                all(req, resp);
                break;
            default:
                break;
        }
    }


    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String method = req.getParameter("method");
        cacheI.add(method);
        setResponse(resp);
        Writer out = resp.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String method = req.getParameter("method");
        cacheI.remove(method);
        setResponse(resp);
        Writer out = resp.getWriter();
        out.write("success");
        out.flush();
        out.close();
    }

    private void size(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer size = cacheI.size();
        setResponse(resp);
        Writer out = resp.getWriter();
        out.write(size.toString());
        out.flush();
        out.close();
    }

    private void cacheName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String method = req.getParameter("method");
        String cacheName = cacheI.getCacheName(method);
        setResponse(resp);
        Writer out = resp.getWriter();
        out.write(cacheName);
        out.flush();
        out.close();
    }

    private void all(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] all = cacheI.getAll();
        setResponse(resp);
        Writer out = resp.getWriter();
        out.write(JSONObject.toJSONString(all));
        out.flush();
        out.close();
    }


    private void setResponse(HttpServletResponse resp){
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("utf-8");
    }

    public StatViewServlet(CacheI cacheI) {
        this.cacheI = cacheI;
    }
}