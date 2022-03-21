package com.example.jimmy.androidcommonframework.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.jimmy.androidcommonframework.utils.LogUtils;

/**
 * Created by wangxibin
 * Date: 2022/3/11
 */
public class PhoneStatusReceiver extends BaseReceiver {

    private final String TAG = "PhoneStatusReceiver";

    private String mIncomingNumber = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.d(TAG, "[Broadcast]=>" + action);
        // 如果是拨打电话
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            LogUtils.d(TAG, "call out phone number:" + phoneNumber);
        } else {
            // 如果是来电
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(Service.TELEPHONY_SERVICE);
            tManager.listen(new PhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
            switch (tManager.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    mIncomingNumber = intent.getStringExtra(
                            TelephonyManager.EXTRA_INCOMING_NUMBER);
                    LogUtils.d(TAG, "RINGING 等待接电话:" + mIncomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    LogUtils.d(TAG, "incoming ACCEPT 通话中:" + mIncomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    LogUtils.d(TAG, "incoming IDLE 电话挂断");
                    break;
            }
        }
    }
}
