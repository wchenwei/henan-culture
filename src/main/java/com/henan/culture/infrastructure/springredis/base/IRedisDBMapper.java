package com.henan.culture.infrastructure.springredis.base;


import com.henan.culture.infrastructure.springredis.util.RedisMapperUtil;

public interface IRedisDBMapper<T> {
    T getId();

    int getServerId();

    default void saveDB() {
        RedisMapperUtil.update(this);
    }

    default void delete() {
        RedisMapperUtil.delete(this);
    }
}
