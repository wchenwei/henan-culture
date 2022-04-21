package com.henan.culture.infrastructure.util;

import com.henan.culture.infrastructure.springredis.RedisDBClient;
import com.henan.culture.infrastructure.springredis.RedisKeyUtils;

/**
 * @author cliff
 * @date 2021/1/27
 */
public class IdManager {
    /**
     * 生成递增id
     *
     * @param idType
     * @return
     */
    public static long genId(IdType idType) {
        return RedisDBClient.getInstance().hincr(RedisKeyUtils.buildKeyName(idType.name()), idType.name(), 1);
    }

    public static String genStrId(IdType idType) {
        return Long.toString(genId(idType), 36);
    }

}
