package com.henan.culture.utils.springredis;

import com.henan.culture.utils.springredis.config.RedisTemplateConfig;
import com.henan.culture.utils.util.SpringUtil;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis数据库操作客户端
 */
public final class RedisDBClient {

    private static final RedisDBClient instance = new RedisDBClient();

    public static RedisDBClient getInstance() {
        return instance;
    }

    private RedisDBClient() {
        RedisTemplateConfig redisConfig = SpringUtil.getBean(RedisTemplateConfig.class);
        this.redisTemplate = redisConfig.createStrTemplate(redisConfig.getDb());
    }

    private RedisTemplate<String, String> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public long hincr(String key, String item, long by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    public long incr(String key, long by) {
        return redisTemplate.opsForValue().increment(key, by);
    }

    public void saveValue(String key, String value, long l, TimeUnit timeUnit) {
        if (l <= 0) {
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, value, l, timeUnit);
        }
    }

    public String getValue(String key, long l, TimeUnit timeUnit) {
        String ret = (String) redisTemplate.opsForValue().get(key);
        if (ret != null && l > 0) {
            redisTemplate.expire(key, l, timeUnit);
        }
        return ret;
    }

    public String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void delValue(String key) {
        redisTemplate.opsForValue().getOperations().delete(key);
    }

    public void saveHashValue(String key, String hashKey, String value) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        ops.put(key, hashKey, value);
    }

    public String getHashValue(String key, String hashKey) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        return ops.get(key, hashKey);
    }

    public List<String> getMultiHashValue(String key, Collection<String> hashKey) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        return ops.multiGet(key, hashKey);
    }

    public void delHashValue(String key, String hashKey) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        ops.delete(key, hashKey);
    }

    public void saveSValue(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public Set<String> getRndValue(String key, int count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    public void delSValue(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    public boolean isSContain(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public void expire(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

}
