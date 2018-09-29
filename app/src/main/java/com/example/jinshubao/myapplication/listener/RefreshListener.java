package com.example.jinshubao.myapplication.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jinshubao.myapplication.R;
import com.example.jinshubao.myapplication.adapter.ImageListAdapter;
import com.example.jinshubao.myapplication.api.ImageApi;
import com.example.jinshubao.myapplication.model.ImageModel;
import com.example.jinshubao.myapplication.observer.CommonObserver;
import com.example.jinshubao.myapplication.utils.RetrofitUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RefreshListener implements OnRefreshListener, OnLoadMoreListener {

    private RefreshLayout refreshLayout;

    private ListView mListView;

    private Context context;

    private int page = 1;
    private int pageSize = 50;

    public RefreshListener(RefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
        this.context = refreshLayout.getContext();
        mListView = refreshLayout.findViewById(R.id.list_view);
        mListView.setAdapter(new ImageListAdapter(this.context, R.id.list_item));
        this.loadData(1, pageSize);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        this.loadData(page, pageSize);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        this.loadData(page++, pageSize);
    }

    private void loadData(final int page, int pageSize) {
        ImageApi imageApi = RetrofitUtils.createApi(ImageApi.class);
        imageApi.images(page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<ImageModel>() {
                    @Override
                    public void onNext(ImageModel resultHelper) {
                        ImageListAdapter adapter = (ImageListAdapter) mListView.getAdapter();
                        List<ImageModel.ImageVo> results = resultHelper.getResults();
                        adapter.clear();
                        adapter.addAll(results);
                        Toast.makeText(context, "加载了" + results.size() + "条数据", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                        refreshLayout.setLoading(false);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        //关闭刷新动画
                        refreshLayout.setRefreshing(false);
                        refreshLayout.setLoading(false);
                    }
                });
    }

}
