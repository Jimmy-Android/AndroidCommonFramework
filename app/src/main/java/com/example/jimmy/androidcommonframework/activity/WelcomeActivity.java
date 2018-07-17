package com.example.jimmy.androidcommonframework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.jimmy.androidcommonframework.R;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Jimmy on 2018/6/28.
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initPageData();
        initPageView();
    }

    @Override
    protected void initPageView() {
        EventBus.getDefault().post(666);
    }

    @Override
    protected void initPageData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
