package com.example.jimmy.androidcommonframework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wangxibin
 * Date: 2022/3/11
 */

/**
 * Android 四大组件之一
 * 实现模式：开发者模式。两个角色：广播发送者和广播接受者。
 * 基于消息的发布/订阅事件模型，将发送者和接收者进行解耦。
 * 作用：
 * 01.
 */
public abstract class BaseReceiver extends BroadcastReceiver {

    @Override
    public abstract void onReceive(Context context, Intent intent);
}
