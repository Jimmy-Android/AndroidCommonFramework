package com.example.jimmy.androidcommonframework.ActivityStudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jimmy.androidcommonframework.R;

/**
 * Created by Jimmy on 2018/4/7.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Intent intent;
    private Button button;
    public MainActivity() {
        super();
        Log.d(TAG,"执行了MainActivity()无参构造方法");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Log.d(TAG,"执行了onCreate()方法");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"执行了onStart()方法");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"执行了onRestart()方法");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"执行了onResume()方法");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"执行了onPause()方法");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"执行了onStop()方法");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"执行了onDestroy()方法");
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(MainActivity.this,ServiceStudyActivity.class);
        this.startActivity(intent);
    }

    private void initView(){
        button = this.findViewById(R.id.sevStuActBtn);
        button.setOnClickListener(this);
    }
}
