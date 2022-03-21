package com.example.jimmy.androidcommonframework.utils;

import android.util.Log;

import java.util.Locale;

/**
 * Created by Jimmy on 2018/4/7.
 */

public class LogUtils {

    private static boolean mLogEnable = true;

    private static String buildMsg(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtils.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread()
                .getId(), caller, msg);
    }

    public static void v(String TAG, String msg) {
        if (mLogEnable) {
            Log.v(TAG, buildMsg(msg));
        }
    }

    public static void d(String TAG, String msg) {
        if (mLogEnable) {
            Log.d(TAG, buildMsg(msg));
        }
    }

    public static void i(String TAG, String msg) {
        if (mLogEnable) {
            Log.i(TAG, buildMsg(msg));
        }
    }

    public static void w(String TAG, String msg) {
        if (mLogEnable) {
            Log.w(TAG, buildMsg(msg));
        }
    }

    public static void e(String TAG, String msg) {
        if (mLogEnable) {
            Log.e(TAG, buildMsg(msg));
        }
    }
}
