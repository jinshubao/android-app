package com.example.jinshubao.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jinshubao.myapplication.R;
import com.example.jinshubao.myapplication.adapter.ImageListAdapter;
import com.example.jinshubao.myapplication.api.ImageApi;
import com.example.jinshubao.myapplication.model.ImageModel;
import com.example.jinshubao.myapplication.observer.CommonObserver;
import com.example.jinshubao.myapplication.utils.RetrofitUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private SmartRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshLayout = findViewById(R.id.refreshLayout);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ImageListAdapter adapter = new ImageListAdapter(this, null);
        recyclerView.setAdapter(adapter);

        RefreshListener refreshListener = new RefreshListener(this, adapter);
        refreshLayout.setOnRefreshListener(refreshListener);
        refreshLayout.setOnLoadMoreListener(refreshListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class RefreshListener implements OnRefreshListener, OnLoadMoreListener {

        private Context context;
        ImageListAdapter adapter;
        private int page = 1;
        private int pageSize = 20;

        private ImageApi imageApi;

        public RefreshListener(Context context, ImageListAdapter adapter) {
            this.context = context;
            this.adapter = adapter;
            this.imageApi = RetrofitUtils.createApi(ImageApi.class);
            this.loadData(1, pageSize, LoadType.REFRESH);
        }

        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            page = 1;
            this.loadData(page, pageSize, LoadType.REFRESH);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            this.loadData(page++, pageSize, LoadType.LOAD);
        }

        private void loadData(final int page, int pageSize, LoadType loadType) {

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

    }

    private enum LoadType {
        REFRESH, LOAD
    }
}
