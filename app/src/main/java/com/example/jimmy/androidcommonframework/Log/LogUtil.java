package com.example.jimmy.androidcommonframework.Log;

import android.util.Log;

import java.util.Locale;

/**
 * Created by Jimmy on 2018/4/7.
 */

public class LogUtil {
    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread()
                .getId(), caller, msg);
    }
//    public static void v(String mess) {
//        Log.v(getTag(), buildMessage(mess));
//    }
//    public static void d(String mess) {
//        if (LOGD) { Log.d(getTag(), buildMessage(mess)); }
//    }
//    public static void i(String mess) {
//        if (LOGI) { Log.i(getTag(), buildMessage(mess)); }
//    }
//    public static void w(String mess) {
//        if (LOGW) { Log.w(getTag(), buildMessage(mess)); }
//    }
//    public static void e(String mess) {
//        if (LOGE) { Log.e(getTag(), buildMessage(mess)); }
//    }
}
