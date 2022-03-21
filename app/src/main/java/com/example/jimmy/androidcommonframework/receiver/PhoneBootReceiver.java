package com.example.jimmy.androidcommonframework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jimmy.androidcommonframework.utils.LogUtils;

/**
 * Created by wangxibin
 * Date: 2022/3/11
 */
public class PhoneBootReceiver extends BaseReceiver {

    private final String TAG = PhoneBootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            LogUtils.d(TAG, "开机完成");
        }
    }
}
