package com.apartcache.starter.config;

import com.apartcache.starter.bean.ApartCache;
import com.apartcache.starter.config.interceptor.CacheAdvice;
import com.apartcache.starter.config.redis.RedisCacheManager;
import com.apartcache.starter.config.redis.RedisClient;
import com.apartcache.starter.manage.CacheI;
import com.apartcache.starter.manage.CacheImpl;
import com.apartcache.starter.support.http.StatViewServlet;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by desk
 *
 * @date 2021/8/9
 */
@Configuration
@EnableConfigurationProperties(ApartCache.class)
@ConditionalOnClass(ApartCache.class)
@ConditionalOnProperty(prefix = "apartcache",name={"apartcache.cacheExpression","apartcache.cachePackage"},matchIfMissing = true)
public class CacheConfiguration {

    @Autowired
    RedisConnectionFactory factory;
    @Autowired
    ApartCache apartCache;

    @Bean
    public CacheNameGenerator cacheNameGenerator(){
        return new CacheNameGenerator();
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object>template=new RedisTemplate<>();
        //关联
        template.setConnectionFactory(factory);

        //设置key的序列化器
        template.setKeySerializer(new StringRedisSerializer());

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance , ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    @Bean
    public AspectJExpressionPointcutAdvisor configurabledvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(apartCache.getCacheExpression());
        advisor.setAdvice(cacheAdvice());
        return advisor;
    }

    @Bean
    public CacheAdvice cacheAdvice(){
        CacheAdvice cacheAdvice = new CacheAdvice(redisCacheManager());
        System.out.println(cacheAdvice.getClass().getClassLoader().getClass().getName());
        return cacheAdvice;
    }

    @Bean
    public RedisCacheManager redisCacheManager(){
        return new RedisCacheManager(redisClient(), cacheServiceManager(), cacheNameGenerator());
    }

    @Bean
    public RedisClient redisClient(){
        return new RedisClient(redisTemplate(factory));
    }

    @Bean
    public CacheServiceManager cacheServiceManager(){
        return new CacheServiceManager(apartCache, cacheNameGenerator());
    }

    @Bean
    public CacheI cacheI(){
        return new CacheImpl(cacheServiceManager());
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean();
        registration.setServlet(new StatViewServlet(cacheI()));
        registration.addUrlMappings("/manage/*");
        return registration;
    }

}
