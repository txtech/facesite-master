package com.nabobsite.modules.nabob.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ：杨过
 * @date ：Created in 2020/2/26
 * @version: V1.0
 * @slogan: 天下风云出我辈，一入代码岁月催
 * @description:
 **/
@Service
public class RedisOpsUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisOpsUtil.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private final static long defaultExpireTime = 2;

    public boolean set(String key, Object value, long timeout, TimeUnit unit){
        redisTemplate.opsForValue().set(key,value,timeout,unit);
        return true;
    }

    public void expire(String key, long timeout, TimeUnit unit){
        redisTemplate.expire(key,timeout,unit);
    }

    public void remove(Collection<String> keys){
        redisTemplate.delete(keys);
    }

    public boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit){
        return redisTemplate.opsForValue().setIfAbsent(key,value,timeout,unit);
    }

    public <T> T get(String key,Class<?> T){
        return (T)redisTemplate.opsForValue().get(key);
    }

    public Long decr(String key){
        return redisTemplate.opsForValue().decrement(key);
    }

    public Long decr(String key,long delta){
        return redisTemplate.opsForValue().decrement(key,delta);
    }

    public Long incr(String key){
        return redisTemplate.opsForValue().increment(key);
    }

    public Long incr(String key,long delta){
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * @描述:读取缓存
     * @时间:2017年8月31日 上午11:56:10
     */
    public Object get(final String key) {
        try {
            if(StringUtils.isBlank(key)){
                return null;
            }
            if(this.exists(key)){
                return redisTemplate.opsForValue().get(key);
            }
        } catch (Exception e) {
            logger.error("读取缓存异常",e);
        }
        return null;
    }

    /**
     * @描述:写入缓存和失效时间
     * @时间:2017年8月31日 上午11:55:55
     */
    public boolean set(final String key, Object value, Long expireTime) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            logger.error("读取缓存异常",e);
        }
        return false;
    }

    /**
     * @描述:写入缓存bean
     * @时间:2017年8月31日 上午11:55:55
     */
    public boolean setBean(String key,Object value,Class clazz){
        try {
            Jackson2JsonRedisSerializer serializer =  new Jackson2JsonRedisSerializer(clazz);
            return this.set(key,serializer.serialize(value));
        } catch (Exception e) {
            logger.error("异常",e);
        }
        return false;
    }

    /**
     * @描述:缓存对象实体
     * @时间:2018年3月2日 下午2:37:22
     */
    /*public boolean setBeanValid(String key, Object value,Long invalidTimer,Class clazz) {
        try {
            Jackson2JsonRedisSerializer serializer =  new Jackson2JsonRedisSerializer(clazz);
            return this.set(key,serializer.serialize(value),invalidTimer);
        } catch (Exception e) {
            logger.error("异常",e);
        }
        return false;
    }
*/
    /**
     * @描述:缓存对象实体
     * @时间:2018年3月2日 下午2:37:22
     */
    public boolean setBeanValid2(String key, Object value,Long invalidTimer,Class clazz) {
        try {
            return this.set(key,FastJsonRedisSerializer.serialize2(value),invalidTimer);
        } catch (Exception e) {
            logger.error("异常",e);
        }
        return false;
    }

    /**
     * @描述:获取缓存对象实体
     * @时间:2018年3月2日 下午2:37:22
     */
    public Object getBean2(String key,Class clazz){
        try {
            if(!exists(key)){
                return null;
            }
            return FastJsonRedisSerializer.deserialize2((byte[])this.get((key)));
        } catch (Exception e) {
            logger.error("异常",e);
        }
        return null;
    }

    /**
     * @描述:获取缓存对象实体
     * @时间:2018年3月2日 下午2:37:22
     */
    /*public <T> T getBean(String key,Class clazz){
        try {
            FastJsonRedisSerializer serializer =  new FastJsonRedisSerializer(clazz);
            return serializer.deserialize((byte[])this.get((key)));
        } catch (Exception e) {
            logger.error("异常",e);
        }
        return null;
    }*/

    /**
     * @描述:写入缓存
     * @时间:2017年8月31日 上午11:56:03
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, defaultExpireTime, TimeUnit.HOURS);
            return true;
        } catch (Exception e) {
            logger.error("写入缓存异常",e);
        }
        return false;
    }

    /**
     * 写入list
     * @param key
     * @param value
     * @return
     */
    public <T> boolean setList(String key,T value,long expireMinutes,Class clazz) {
        try {
            // return this.set(key,SerializerUtils.serializeList(value,clazz),expireMinutes*60*1000 );
            return this.set(key,FastJsonRedisSerializer.serialize2(value),expireMinutes*60*1000 );
        } catch (Exception e) {
            logger.error("写入list缓存异常",e);
        }
        return false;
    }

    /**
     * 默认缓存列表两小时
     */
    public <T> boolean setList(String key, List<T> value,Class clazz) {
        return setList(key,value,120,clazz);
    }

    /**
     * 读取list缓存
     * @param key
     * @return
     */
    public <T> T getList(String key,Class claszz){
        try {
            if(!exists(key)){
                return null;
            }
            //return SerializerUtils.unserializeForList((byte[])this.get((key)),claszz);
            return (T)FastJsonRedisSerializer.deserialize2((byte[])this.get((key)));
        } catch (Exception e) {
            logger.error("读取list缓存异常",e);
        }
        return null;
    }


    /**
     * @描述:删除缓存
     * @时间:2017年8月31日 上午11:56:24
     */
    public boolean remove(String key) {
        try {
            if (exists(key)) {
                redisTemplate.delete(key);
            }
            return true;
        } catch (Exception e) {
            logger.error("删除缓存异常",e);
        }
        return false;
    }

    /**
     * @描述:批量删除key
     * @时间:2017年8月31日 上午11:56:31
     */
    public boolean removePattern(String pattern) {
        try {
            Set<Serializable> keys = redisTemplate.keys(pattern);
            if (keys.size() > 0) {
                redisTemplate.delete(keys);
            }
            return true;
        } catch (Exception e) {
            logger.error("删除缓存异常",e);
        }
        return false;
    }

    /**
     * @描述:判断缓存是否存在
     * @时间:2017年8月31日 上午11:56:17
     */
    public boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("判断缓存是否存在异常",e);
        }
        return false;
    }

    /**
     * @描述:向key对应的map中添加缓存对象  field map对应的key
     * @时间:2017年8月31日 下午6:34:15
     */
    public boolean addMap(String key, String field, Object value){
        try {
            redisTemplate.opsForHash().put(key, field, value);
            redisTemplate.expire(key, defaultExpireTime, TimeUnit.HOURS);
            return true;
        } catch (Exception e) {
            logger.error("写入缓存异常",e);
        }
        return false;
    }

    /**
     * @描述:将map写入缓存
     * @时间:2017年8月31日 下午6:38:31
     */
    public boolean setMap(String key, Map<Object, Object> map){
        try {
            HashOperations<Serializable,Object,Object> operations = redisTemplate.opsForHash();
            redisTemplate.expire(key, defaultExpireTime, TimeUnit.HOURS);
            operations.putAll(key,map);
            return true;
        } catch (Exception e) {
            logger.error("写入缓存异常",e);
        }
        return false;
    }


    /**
     * @描述:将map写入缓存
     * @时间:2017年8月31日 下午6:38:31
     */
    public boolean setMapValid(String key, Map<String, Integer> map,Long invalidTimer) {
        try {
            HashOperations<Serializable,String,Integer> operations = redisTemplate.opsForHash();
            redisTemplate.expire(key, invalidTimer,TimeUnit.MILLISECONDS);
            operations.putAll(key,map);
            return true;
        } catch (Exception e) {
            logger.error("写入缓存异常",e);
        }
        return false;
    }

    /**
     * @描述:获取map缓存
     * @时间:2017年8月31日 下午6:42:03
     */
    public  Map<String, Integer> getMapValid(String key){
        try {
            if(StringUtils.isBlank(key)){
                return null;
            }
            if(!this.exists(key)){
                return null;
            }
            HashOperations<Serializable,String,Integer> operations = redisTemplate.opsForHash();
            return operations.entries(key);
        } catch (Exception e) {
            logger.error("获取map缓存 异常",e);
        }
        return null;
    }

    /**
     * @描述:获取map缓存
     * @时间:2017年8月31日 下午6:42:03
     */
    public  Map<Object, Object> getMap(String key){
        try {
            if(StringUtils.isBlank(key)){
                return null;
            }
            if(!this.exists(key)){
                return null;
            }
            HashOperations<Serializable,Object,Object> operations = redisTemplate.opsForHash();
            return operations.entries(key);
        } catch (Exception e) {
            logger.error("获取map缓存 异常",e);
        }
        return null;
    }

    /**
     * @描述:清理缓存
     * @时间:2018年5月29日 下午4:17:42
     */
    public  boolean cleanKey(String key){
        try {
            this.remove(key);
            return true;
        } catch (Exception e) {
            logger.error("清理缓存异常",e);
        }
        return false;
    }

}
