package com.example.jinshubao.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jinshubao.myapplication.R;
import com.example.jinshubao.myapplication.adapter.ImageListAdapter;
import com.example.jinshubao.myapplication.listener.RefreshListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        SmartRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new ImageListAdapter(this, null));

        RefreshListener refreshListener = new RefreshListener(this, refreshLayout);
        refreshLayout.setOnRefreshListener(refreshListener);
        refreshLayout.setOnLoadMoreListener(refreshListener);
    }
}
