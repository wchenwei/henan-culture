package com.henan.culture.infrastructure.springredis;


import com.henan.culture.infrastructure.util.Constants;

import java.util.ResourceBundle;

public class RedisKeyUtils {

    public static String buildKeyName(String key) {
        return Constants.gamePrefix + "_" + key;
    }
}
