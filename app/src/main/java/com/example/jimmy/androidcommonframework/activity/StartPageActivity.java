package com.example.jimmy.androidcommonframework.activity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jimmy.androidcommonframework.R;
import com.example.jimmy.androidcommonframework.utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Jimmy on 2018/6/30.
 */

public class StartPageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initPageData();
        initPageView();
    }

    @Override
    protected void initPageView() {
        imageView = (ImageView) this.findViewById(R.id.imv);
        imageView.setOnClickListener(this);
        reportFullyDrawn();
    }

    @Override
    protected void initPageData() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在界面销毁的地方要解绑
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Subscribe
    public void getEventBus(Integer num) {
        if (num != null) {
            Toast.makeText(this, "num" + num, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imv:
                startActivity(IntentUtils.toWelcomeActivity(this));
                break;
            default:
                break;
        }
    }
}

