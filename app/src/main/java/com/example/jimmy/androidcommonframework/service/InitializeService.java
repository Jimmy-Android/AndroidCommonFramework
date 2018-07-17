package com.example.jimmy.androidcommonframework.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Jimmy on 2018/6/30.
 * 用来解决App冷启动的一种方式
 * 继承IntentService来初始化一些第三方库和全局的实例，解决App启动过慢
 * 不过什么时候加载完所有的数据，我们无法掌控，可能导致进入主页的NullPointerException
 */

public class InitializeService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitializeService(String name) {
        super(name);
    }

    public InitializeService() {
        super("InitializeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null){
            final String action = intent.getAction();
            if(action.equals("")){
                //进行第三方库的初始化
            }
        }
    }
}
