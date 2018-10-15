package com.example.jinshubao.myapplication.utils;

import android.support.annotation.NonNull;

import com.example.jinshubao.myapplication.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENV)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    public static <T> T createApi(@NonNull Class<T> tClass) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(logging);
        }
//        clientBuilder.addInterceptor(new OAuthTokenInterceptor());
        builder.client(clientBuilder.build());
        return builder.build()
                .create(tClass);
    }
}
