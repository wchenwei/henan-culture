package com.henan.culture.infrastructure.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

import java.util.List;

public enum JSONUtil {
    ;

    /**
     * 将JSON字符串转为Java对象
     */
    public static <T> T toJavaObject(String result, Class<T> clazz) {
        return JSONObject.toJavaObject(JSONObject.parseObject(result), clazz);
    }

    /**
     * JSON字符串对象解析成java List对象
     */
    public static <T> List<T> toJavaList(String resultList, Class<T> clazz) {
        return JSONArray.parseArray(resultList).toJavaList(clazz);
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T fromJson(String str, Class<T> cls) {
        return JSON.parseObject(str, cls);
    }

    public static <T> T fromJson(String str, TypeReference<T> type) {
        return JSON.parseObject(str, type, new Feature[0]);
    }
}
