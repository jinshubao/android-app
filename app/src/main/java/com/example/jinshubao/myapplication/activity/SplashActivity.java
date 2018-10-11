package com.example.jinshubao.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jinshubao.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this::skipSplash, 3000);
    }

    public void skipSplash() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
