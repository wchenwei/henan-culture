package com.henan.culture.utils.springredis.base;


import com.henan.culture.utils.springredis.util.RedisMapperUtil;

import java.util.List;

/**
 * @description: 抽象基础类 需被继承
 * @author: chenwei
 * @create: 2020-09-25 14:23
 **/
public abstract class BaseEntityMapper<T> extends DBEntity<T> implements IRedisDBMapper {

    public static <T> List<T> queryListAll(int serverId, Class<T> entity) {
        return RedisMapperUtil.queryListAll(serverId, entity);
    }

    public static <T> List<T> queryListByPrimkeys(Object serverId, List<Object> primKeys, Class<T> entityClass) {
        return RedisMapperUtil.queryListByPrimkeys(serverId, primKeys, entityClass);
    }

    public static <T> T queryOne(Object serverId, Object id, Class<T> entity) {
        return RedisMapperUtil.queryOne(serverId, id, entity);
    }

    public static <T extends IRedisDBMapper> void deleteAll(int serverId, Class<T> entity) {
        RedisMapperUtil.deleteAll(serverId, entity);
    }

    public static <T> List<T> queryList(Object serverId, List<Object> ids, Class<T> entity) {
        return RedisMapperUtil.queryListByPrimkeys(serverId, ids, entity);
    }
}
