package com.henan.culture.utils.springredis.base;


import com.henan.culture.utils.springredis.util.RedisMapperUtil;

public interface IRedisDBMapper<T> {
    T getId();

    default int getServerId(){
        return 0;
    };

    default void saveDB() {
        RedisMapperUtil.update(this);
    }

    default void delete() {
        RedisMapperUtil.delete(this);
    }
}
