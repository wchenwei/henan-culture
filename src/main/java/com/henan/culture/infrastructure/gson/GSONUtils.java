package com.henan.culture.infrastructure.gson;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

// ************************************************************************
// helper class for manipulating GSON / JSON

public class GSONUtils {

    // ************************************************************************
    // constants
    // ------------------------------------------------------------------------
    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    private static Gson gson = new GsonBuilder()
            .setExclusionStrategies(new IgnoreAnnotationExclusionStrategy())
            .setDateFormat(DATEFORMAT)
            .create();

    // ************************************************************************
    // public methods

    // ------------------------------------------------------------------------
    public static String ToJSONString(Object object) {
        return gson.toJson(object);
    }

    // ------------------------------------------------------------------------
    public static <T> T FromJSONString(String jsonString, Class<T> classOfT) {

        return gson.fromJson(jsonString, classOfT);
    }

    // ------------------------------------------------------------------------
    public static <T> T FromJSONString(String jsonString, Type typeOfT) {

        return gson.fromJson(jsonString, typeOfT);
    }

    // ------------------------------------------------------------------------
    // Ideal for light-weight objects
    // For bigger objects be aware of performance overhead
    public static Object DuplicateObject(Object object) {
        return new Gson().fromJson(GSONUtils.ToJSONString(object), object.getClass());
    }

    // ------------------------------------------------------------------------
    public static <T> T BuildObjectFromJSON(Gson gson, String stringJSON, Class<T> classOfT) {
        if (null != stringJSON) {
            T result = gson.fromJson(stringJSON, classOfT);

            return result;
        } else {
            try {
                return classOfT.newInstance();
            } catch (Exception e) {
//				LogManager.error( "Cannot create instance of class " + classOfT + ", exception : " + e );
            }
        }

        return null;
    }
}
