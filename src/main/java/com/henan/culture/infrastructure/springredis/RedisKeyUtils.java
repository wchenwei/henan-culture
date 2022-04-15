package com.henan.culture.infrastructure.springredis;


import java.util.ResourceBundle;

public class RedisKeyUtils {

    public static String buildKeyName(String key) {
        return ResourceBundle.getBundle("game.prefix") + "_" + key;
    }
}
