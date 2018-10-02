package com.example.jinshubao.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

public class SPUtils {

    public static final String LIST_PAGE_NUM = "LIST_PAGE_NUM";
    public static final String LIST_PAGE_SIZE = "LIST_PAGE_SIZE";


    private static SharedPreferences preferences = null;

    public static void init(Context context) {
        preferences = context.getSharedPreferences("token", Activity.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        if (value != null) {
            Objects.requireNonNull(preferences, "preferences 未初始化").edit().putString(key, value).apply();
        }
    }

    public static String getString(String key, String defValue) {
        return Objects.requireNonNull(preferences, "preferences 未初始化").getString(key, defValue);
    }


    public static void putInt(String key, int value) {
        Objects.requireNonNull(preferences, "preferences 未初始化").edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return Objects.requireNonNull(preferences, "preferences 未初始化").getInt(key, defValue);
    }

}
