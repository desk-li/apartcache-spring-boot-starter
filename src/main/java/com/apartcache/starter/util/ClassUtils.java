package com.apartcache.starter.util;

import com.apartcache.starter.bean.ParamBean;
import org.apache.commons.lang3.ClassLoaderUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by desk
 *
 * @date 2021/8/12
 */
public class ClassUtils {

    public static final String rex = "\\(.*\\)";
    public static final String rep = "\\<[^>]*[^<]*\\>";
    public static final Pattern compile = Pattern.compile(rex);
    public static final ClassLoader loader = ClassLoader.getSystemClassLoader();

    public static void main(String[] args) {
        String ss = "com.apartcache.starter.manage.CacheI#add(java.lang.String method)";
        String s2 = "com.apartcache.starter.manage.CacheI#change(java.util.List<String> list, java.util.Map<String, Object> map)";
        getParams(s2);

    }

    public static Method toMethod(String s){
        String sClass = s.substring(0, s.indexOf("#"));
        String mName = s.substring(s.indexOf("#")+1, s.indexOf("("));
        try {
            Class<?> aClass = loader.loadClass(sClass);
            List<Class> mClass = getParams(s).stream().map(ParamBean::getParamClass).collect(Collectors.toList());
            return aClass.getMethod(mName, mClass.toArray(new Class[mClass.size()]));
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static List<ParamBean> getParams(String s){
        String p = null;
        for(Matcher matcher = compile.matcher(s); matcher.find(); p = matcher.group()) {
        }
        p = p.replaceAll(rep, "").replaceAll("\\(","").replaceAll("\\)","");
        if(StringUtils.isNoneBlank(p)){
            String[] arr = p.split(",");
            List<ParamBean> list = new ArrayList<>(arr.length);
            for (int i = 0; i < arr.length; i++) {
                String[] s1 = arr[i].trim().split(" ");
                list.add(toParamBean(s1));
            }
            return list;
        }
        return Collections.EMPTY_LIST;
    }



    private static ParamBean toParamBean(String[] s1){
        try {
            Class<?> aClass = loader.loadClass(s1[0].trim());
            return new ParamBean(aClass, s1[1].trim());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
