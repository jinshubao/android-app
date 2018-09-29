package com.example.jinshubao.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

public class SPUtils {

    public static final String TOKEN_KEY = "access_token";
    public static final String TOKEN_TYPE = "token_type";
    public static final String TOKEN_EXPIRES_IN = "expires_in";
    public static final String TOKEN_REFRESH_TOKEN = "refresh_token";

    private static SharedPreferences preferences = null;

    public static void init(Context context) {
        preferences = context.getSharedPreferences("token", Activity.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        Objects.requireNonNull(preferences, "preferences 未初始化").edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return Objects.requireNonNull(preferences, "preferences 未初始化").getString(key, null);
    }

}
