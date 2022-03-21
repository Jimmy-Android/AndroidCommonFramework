package com.example.jimmy.androidcommonframework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jimmy.androidcommonframework.utils.LogUtils;

/**
 * Created by wangxibin
 * Date: 2022/3/11
 */
public class NetworkChangeReceiver extends BaseReceiver {

    private final String TAG = NetworkChangeReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.d(TAG, "网络状态发生改变");
    }
}
