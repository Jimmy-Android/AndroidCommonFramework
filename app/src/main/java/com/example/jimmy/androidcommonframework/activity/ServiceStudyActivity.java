package com.example.jimmy.androidcommonframework.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jimmy.androidcommonframework.R;

import androidx.annotation.Nullable;

/**
 * Created by Jimmy on 2018/4/7.
 */

public class ServiceStudyActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "ServiceStudyActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicestudy);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
    }

    @Override
    public void onClick(View v) {
        String viewId = String.valueOf(v.getId());
        switch (v.getId()){
//            case "startServiceBtn":
//                break;
//            case "":
//                break;
//            case "":
//                break;
//            case "":
//                break;
            default:
                Log.d(TAG,viewId);
                break;
        }
    }

    private void initView(){

    }
}
