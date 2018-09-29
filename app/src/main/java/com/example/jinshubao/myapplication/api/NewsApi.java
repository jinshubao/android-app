package com.example.jinshubao.myapplication.api;


import com.example.jinshubao.myapplication.BuildConfig;
import com.example.jinshubao.myapplication.model.ApiNewsResultListHelper;
import com.example.jinshubao.myapplication.model.NewsModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsApi {


    @GET(BuildConfig.NEWS_API + "/nc/article/headline/T1348647853363/{startPage}-{endPage}.html")
    Observable<ApiNewsResultListHelper<NewsModel>> news(@Path("startPage") int startPage, @Path("endPage") int endPage);
}
