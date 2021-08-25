package com.apartcache.starter.config;

import com.apartcache.starter.bean.ApartCache;
import com.apartcache.starter.config.filter.MyFilter;
import com.apartcache.starter.config.filter.MyListener;
import com.apartcache.starter.config.interceptor.CacheAdvice;
import com.apartcache.starter.config.redis.RedisCacheManager;
import com.apartcache.starter.config.redis.RedisClient;
import com.apartcache.starter.manage.CacheI;
import com.apartcache.starter.manage.CacheImpl;
import com.apartcache.starter.mapper.CacheDataMapper;
import com.apartcache.starter.support.http.StatViewServlet;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.sql.DataSource;

/**
 * Created by desk
 *
 * @date 2021/8/9
 */
@Configuration
@EnableConfigurationProperties({ApartCache.class})
@ConditionalOnClass({ApartCache.class})
@ConditionalOnProperty(prefix = "apartcache",name={"apartcache.cacheExpression","apartcache.cachePackage"},matchIfMissing = true)
public class CacheConfiguration {

    @Autowired
    RedisConnectionFactory factory;
    @Autowired
    ApartCache apartCache;
    @Autowired
    SqlSession sqlSession;
    @Autowired
    SqlSessionFactory sqlSessionFactory;

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
    public ServiceManager cacheServiceManager(){
        return new CacheServiceManager(apartCache, cacheNameGenerator());
    }

    @Bean
    public ServiceManager dBServiceManager(){
        sqlSessionFactory.getConfiguration().addMapper(CacheDataMapper.class);
        return new DBServiceManager(sqlSession.getMapper(CacheDataMapper.class), apartCache, cacheNameGenerator());
    }

    @Bean
    public CacheI cacheI(){
        if(dBServiceManager() != null){
            return new CacheImpl(dBServiceManager());
        }
        return new CacheImpl(cacheServiceManager());
    }

    /**
     * 
     * servlet注册
     * @author desk-li 
     * @date 2021/8/25 16:16 
     * @return org.springframework.boot.web.servlet.ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean();
        registration.setServlet(new StatViewServlet(cacheI()));
        registration.addUrlMappings("/manage/*");
        return registration;
    }

    /**
     * 
     * 过滤器注册 
     * @author desk-li 
     * @date 2021/8/25 16:16 
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        MyFilter filter = new MyFilter();
        bean.setFilter(filter);
        bean.setName("myFilter");
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }


    /**
     *
     * servlet请求监听器
     * @author desk-li
     * @date 2021/8/25 16:23
     * @return org.springframework.boot.web.servlet.ServletListenerRegistrationBean
     */
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        MyListener listener = new MyListener();
        bean.setListener(listener);
        bean.setOrder(1);
        return bean;
    }

}
