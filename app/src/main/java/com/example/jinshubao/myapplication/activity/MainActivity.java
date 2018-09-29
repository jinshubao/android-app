package com.example.jinshubao.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

        RefreshListener refreshListener = new RefreshListener(this, R.id.refreshLayout, R.id.recyclerView);
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
        private SmartRefreshLayout refreshLayout;
        private RecyclerView recyclerView;
        private int page = 1;
        private int pageSize = 50;

        public RefreshListener(Context context, int refreshLayoutId, int listId) {
            this.context = context;
            this.refreshLayout = ((Activity) context).findViewById(refreshLayoutId);
            this.recyclerView = ((Activity) context).findViewById(listId);
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
                            refreshLayout.finishRefresh(false);
                            refreshLayout.finishLoadMore(false);
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {
                            //关闭刷新动画
                            refreshLayout.finishRefresh();
                            refreshLayout.finishLoadMore();
                        }
                    });
        }

    }
}
