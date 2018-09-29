package com.example.jinshubao.myapplication.utils;

import com.google.gson.GsonBuilder;

public abstract class JSONUtils {

    public static <T> T fromJson(String json, Class<T> resultClass) {
        return new GsonBuilder().create().fromJson(json, resultClass);
    }

    public static String toJson(Object object) {
        return new GsonBuilder().create().toJson(object);
    }
}
