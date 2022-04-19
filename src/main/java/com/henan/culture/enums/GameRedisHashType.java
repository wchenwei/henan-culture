package com.henan.culture.enums;

import cn.hutool.core.convert.Convert;
import com.henan.culture.infrastructure.gson.GSONUtils;
import com.henan.culture.infrastructure.springredis.RedisDBClient;
import com.henan.culture.infrastructure.springredis.RedisKeyUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 游戏按照hash存储数据
 *
 * @author 司云龙
 * @Version 1.0.0
 * @date 2021/8/25 10:37
 */
public enum GameRedisHashType {
    Player2Server("player2server", "玩家id的游戏物理机"),
    ;

    private GameRedisHashType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }


    public void put(Object field, Object value) {
        buildRedisTemplate().opsForHash().put(buildKey(), field.toString(), GSONUtils.ToJSONString(value));
    }

    public String get(Object field) {
        Object val = buildRedisTemplate().opsForHash().get(buildKey(), field.toString());
        return val != null ? val.toString() : null;
    }

    public long getLong(Object field, long defaultVal) {
        return Convert.toLong(get(field), defaultVal);
    }

    public long getInt(Object field, int defaultVal) {
        return Convert.toInt(get(field), defaultVal);
    }

    public boolean hasKey(Object field) {
        return buildRedisTemplate().opsForHash().hasKey(buildKey(), field.toString());
    }

    public long getLong(Object field) {
        return Convert.toLong(get(field), 0L);
    }

    public int getInt(Object field) {
        return Convert.toInt(get(field), 0);
    }

    public <T> T getObj(Object field, Class<T> classOfT) {
        Object val = get(field);
        if (val != null) {
            return GSONUtils.FromJSONString(val.toString(), classOfT);
        }
        return null;
    }

    public Set<String> getAllKeys() {
        return buildRedisTemplate().opsForHash().keys(buildKey());
    }

    public Map<String, String> getAllVal() {
        return buildRedisTemplate().opsForHash().entries(buildKey());
    }

    public void delHKey(Object... field) {
        if (field.length <= 0) {
            return;
        }
        String[] keys = new String[field.length];
        for (int i = 0; i < field.length; i++) {
            keys[i] = field[i].toString();
        }
        buildRedisTemplate().opsForHash().delete(buildKey(), keys);
    }

    public long incrementKey(Object field, long addVal) {
        return buildRedisTemplate().opsForHash().increment(buildKey(), field.toString(), addVal);
    }


    public void dropColl() {
        buildRedisTemplate().delete(buildKey());
    }

    public void putBatch(Map<String, String> data) {
        buildRedisTemplate().opsForHash().putAll(buildKey(), data);
    }

    public List<String> getListObj(List<String> keys) {
        return buildRedisTemplate().opsForHash().multiGet(buildKey(), keys);
    }

    private String key;
    private String desc;


    public String buildKey() {
        return RedisKeyUtils.buildKeyName(key);
    }

    public RedisTemplate buildRedisTemplate() {
        return RedisDBClient.getInstance().getRedisTemplate();
    }
}










