package com.example.jimmy.androidcommonframework.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.jimmy.androidcommonframework.R;
import com.example.jimmy.androidcommonframework.receiver.PhoneStatusReceiver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Jimmy on 2018/4/7.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Intent intent;
    private Button button;
    private BroadcastReceiver mBroadcastReceiver;
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


    /**
     * 动态注册BroadcastReceiver
     * @param v
     */
    public void registerIt(View v) {
        Log.i(TAG, "registerIt");
        mBroadcastReceiver = new PhoneStatusReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        intentFilter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    /**
     * 页面销毁反注册（注销）BroadcastReceiver
     * @param v
     */
    public void unregisterIt(View v) {
        Log.i(TAG, "unregisterIt");
        unregisterReceiver(mBroadcastReceiver);
    }
}
