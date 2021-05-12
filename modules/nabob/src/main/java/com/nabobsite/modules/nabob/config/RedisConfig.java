/**
 * @类名称:RedisConfig.java
 * @时间:2018年5月29日下午3:32:35
 * @版权:公司 Copyright (c) 2018
 */
package com.nabobsite.modules.nabob.config;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @desc
 * @author nada
 * @create 2020/12/21 11:46 上午
*/
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //使用fastjson序列化
        @SuppressWarnings("rawtypes")
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(fastJsonRedisSerializer);
        template.setHashKeySerializer(fastJsonRedisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}

