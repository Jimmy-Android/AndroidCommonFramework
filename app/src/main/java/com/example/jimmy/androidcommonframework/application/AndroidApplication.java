package com.example.jimmy.androidcommonframework.application;

import android.app.Application;

/**
 * Created by Jimmy on 2018/6/30.
 * Android系统会为每个应用程序创建一个Application实例
 * Application实例是单例的
 * Application的作用：1、组件之间的数据传递 2、数据的缓存
 */

public class AndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
