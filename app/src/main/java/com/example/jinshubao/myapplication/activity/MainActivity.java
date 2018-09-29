package com.example.jinshubao.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jinshubao.myapplication.R;
import com.example.jinshubao.myapplication.adapter.BannerListAdapter;
import com.example.jinshubao.myapplication.api.IndexApi;
import com.example.jinshubao.myapplication.model.ApiResultListHelper;
import com.example.jinshubao.myapplication.model.Banner;
import com.example.jinshubao.myapplication.observer.CommonObserver;
import com.example.jinshubao.myapplication.utils.RetrofitUtils;
import com.example.jinshubao.myapplication.utils.SPUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SPUtils.init(this);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(new BannerListAdapter(MainActivity.this, R.layout.banner_item));

        loadData();
    }

    protected void loadData() {
        IndexApi indexApi = RetrofitUtils.createApi(IndexApi.class);
        indexApi.bannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonObserver<ApiResultListHelper<Banner>>() {
                    @Override
                    public void onNext(ApiResultListHelper<Banner> resultHelper) {
                        BannerListAdapter listAdapter = (BannerListAdapter) listView.getAdapter();
                        listAdapter.addAll(resultHelper.getResultList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
