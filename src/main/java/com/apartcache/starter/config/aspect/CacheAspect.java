package com.apartcache.starter.config.aspect;

import com.apartcache.starter.config.redis.RedisCacheManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Created by desk
 *
 * @date 2021/8/10
 */
public class CacheAspect {

    @Autowired
    HttpServletRequest request;

    @Autowired
    RedisCacheManager redisCacheManager;

    public void pointCut(){}


    public Object doCache(ProceedingJoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        //拦截的方法名称
        String methodName = joinPoint.getSignature().getName();
        //拦截的方法参数
        Object[] args = joinPoint.getArgs();
        //拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature)joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method = null;
            //通过反射获得拦截的method
        try {
            method = target.getClass().getMethod(methodName, parameterTypes);

            Object o = redisCacheManager.searchCache(method, args);

            if(Optional.ofNullable(o).isPresent()){
                return o;
            }

            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }
}
