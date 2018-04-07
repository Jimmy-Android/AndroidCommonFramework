package com.example.jimmy.androidcommonframework.ServiceStudy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Jimmy on 2018/4/7.
 * 练习Android Service创建，绑定以及生命周期等内容
 */

public class MyServiceOne extends Service {

    private static final String TAG = "MyServiceOne";

    public MyServiceOne() {
        super();
        Log.d(TAG,"调用MyServiceOne()构造方法");
    }

    //只在service创建的时候调用一次，可以在此进行一些一次性的初始化操作
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"MyServiceOne执行了onCreate()方法...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"MyServiceOne执行了onStartCommand()方法...");
        //当其他组件调用startService()方法时，此方法将会被调用
        //在这里进行这个service主要的操作
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"MyServiceOne执行了onDestroy()方法，Service被销毁...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"MyServiceOne执行了onBind()方法...");
        //当其他组件调用bindService()方法时，此方法将会被调用
        //如果不想让这个service被绑定，在此返回null即可
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"MyServiceOne执行了onUnbind()方法...");
        return super.onUnbind(intent);
    }
}
