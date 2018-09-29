package com.example.jinshubao.myapplication.api;

import com.example.jinshubao.myapplication.BuildConfig;
import com.example.jinshubao.myapplication.model.ImageModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImageApi {

    @GET(BuildConfig.IMAGE_API + "/api/data/%E7%A6%8F%E5%88%A9/{pageSize}/{page}")
    Observable<ImageModel> images(@Path("page") int page, @Path("pageSize") int pageSize);
}
