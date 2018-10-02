package com.example.jinshubao.myapplication.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.jinshubao.myapplication.R;
import com.example.jinshubao.myapplication.adapter.ImageListAdapter;
import com.example.jinshubao.myapplication.api.ImageApi;
import com.example.jinshubao.myapplication.model.ImageModel;
import com.example.jinshubao.myapplication.observer.CommonObserver;
import com.example.jinshubao.myapplication.utils.RetrofitUtils;
import com.example.jinshubao.myapplication.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RefreshListener implements OnRefreshListener, OnLoadMoreListener {

    private Context context;
    private SmartRefreshLayout refreshLayout;
    private ImageListAdapter adapter;
    private int page;
    private int pageSize;

    private ImageApi imageApi;

    public RefreshListener(Context context, SmartRefreshLayout refreshLayout) {
        this.context = context;
        this.refreshLayout = refreshLayout;
        RecyclerView recyclerView = refreshLayout.findViewById(R.id.recyclerView);
        this.adapter = (ImageListAdapter) recyclerView.getAdapter();
        this.page = SPUtils.getInt(SPUtils.LIST_PAGE_NUM, 1);
        this.pageSize = SPUtils.getInt(SPUtils.LIST_PAGE_SIZE, 20);
        this.imageApi = RetrofitUtils.createApi(ImageApi.class);
        this.loadData(page, pageSize, LoadType.REFRESH);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = page > 1 ? page - 1 : 1;
        this.loadData(page, pageSize, LoadType.REFRESH);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.loadData(page++, pageSize, LoadType.LOAD);
    }

    private void loadData(int page, int pageSize, LoadType loadType) {
        imageApi.images(page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<ImageModel>() {
                    @Override
                    public void onNext(ImageModel resultHelper) {
                        List<ImageModel.ImageVo> results = resultHelper.getResults();
                        if (loadType == LoadType.REFRESH) {
                            adapter.clear();
                        }
                        adapter.addAll(results);
                    }

                    @Override
                    public void onError(Throwable e) {
                        finish(loadType, false);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        finish(loadType, true);
                        SPUtils.putInt(SPUtils.LIST_PAGE_NUM, page);
                        SPUtils.putInt(SPUtils.LIST_PAGE_SIZE, pageSize);
                    }
                });
    }

    private void finish(LoadType loadType, boolean success) {
        if (loadType == LoadType.REFRESH) {
            refreshLayout.finishRefresh(success);
        } else {
            refreshLayout.finishLoadMore(success);
        }
    }

    private enum LoadType {
        REFRESH, LOAD
    }
}


