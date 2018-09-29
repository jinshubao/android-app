package com.example.jinshubao.myapplication.api;

import com.example.jinshubao.myapplication.model.ApiResultListHelper;
import com.example.jinshubao.myapplication.model.Banner;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IndexApi {

    @GET("/api/banner/list")
    Observable<ApiResultListHelper<Banner>> bannerList();

}
