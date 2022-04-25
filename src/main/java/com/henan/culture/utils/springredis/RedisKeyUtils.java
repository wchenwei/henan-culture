package com.henan.culture.utils.springredis;


import com.henan.culture.utils.util.Constants;

public class RedisKeyUtils {

    public static String buildKeyName(String key) {
        return Constants.gamePrefix + "_" + key;
    }
}
