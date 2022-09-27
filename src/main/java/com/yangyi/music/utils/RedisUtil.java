package com.yangyi.music.utils;

import ch.qos.logback.core.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
 * @Author YangYi
 * @Date 2022/8/10 13:26
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存基本对象
     * @param key 键值
     * @param value 值
     * @param <T>
     */
    public <T> void setCacheObject(final String key,final T value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 缓存对象设置过期时间
     * @param key
     * @param value
     * @param timeout 过期时间，单位分钟
     * @param <T>
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout){
        redisTemplate.opsForValue().set(key,value,timeout,TimeUnit.MINUTES);
    }

    /**
     * 判断Key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通获取
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
}
