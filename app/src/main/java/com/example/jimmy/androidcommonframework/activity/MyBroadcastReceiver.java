package com.example.jimmy.androidcommonframework.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;

import com.example.jimmy.androidcommonframework.utils.LogUtils;

/**
 * Created by wangxibin
 * Date: 2022/3/11
 */
public class MyBroadcastReceiver extends Activity {
    public final static String TAG = "MyBroadcastReceiver";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 按钮-监听电话
     * @param v
     */
    public void createPhoneListener(View v) {
        TelephonyManager telephony = (TelephonyManager)getSystemService(
                Context.TELEPHONY_SERVICE);
        telephony.listen(new OnePhoneStateListener(),
                PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 电话状态监听.
     * @author stephen
     *
     */
    class OnePhoneStateListener extends PhoneStateListener{
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            LogUtils.i(TAG, "[Listener]电话号码:"+incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    LogUtils.i(TAG, "[Listener]等待接电话:"+incomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    LogUtils.i(TAG, "[Listener]电话挂断:"+incomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    LogUtils.i(TAG, "[Listener]通话中:"+incomingNumber);
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }


}
