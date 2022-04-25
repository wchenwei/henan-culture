package com.henan.culture.utils.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class IgnoreAnnotationExclusionStrategy implements ExclusionStrategy {

    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(IgnoreGson.class) != null;
    }

    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(IgnoreGson.class) != null;
    }

}