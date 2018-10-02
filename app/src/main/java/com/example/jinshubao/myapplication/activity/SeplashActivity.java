package com.example.jinshubao.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jinshubao.myapplication.R;

public class SeplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seplash);
    }

    public void skipSplash(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
