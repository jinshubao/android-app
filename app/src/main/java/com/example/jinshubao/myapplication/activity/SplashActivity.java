package com.example.jinshubao.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jinshubao.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    private boolean skip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> this.skipSplash(null), 3000);
    }

    public void skipSplash(View view) {
        if (!skip) {
            skip = true;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }
}
