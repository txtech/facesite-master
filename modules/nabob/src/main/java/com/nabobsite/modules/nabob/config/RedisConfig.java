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

        //老得方式
        /*template.setConnectionFactory(redisConnectionFactory);
        // 序列化后会产生java类型说明，如果不需要用“Jackson2JsonRedisSerializer”和“ObjectMapper ”配合效果更好
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        // value值的序列化采用jackson2JsonRedisSerializer
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();*/
        return template;
    }

    /*public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        // logger.info("JedisPool注入成功,redis地址：" + host + ":" + port);
        return jedisPool;
    }*/
}

