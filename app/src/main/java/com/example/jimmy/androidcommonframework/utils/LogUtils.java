package com.example.jimmy.androidcommonframework.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static boolean CAN_OUTPUT_LOG = true;

    /**
     * 创建手机Log并输出Log
     *
     * @param logValue
     */
    public static void writeLogWithCallBack(String logValue) {
        try {
            if (CAN_OUTPUT_LOG) {
                //获取手机本身存储根目录Environment.getExternalStoragePublicDirectory("")
                //sd卡根目录Environment.getExternalStorageDirectory()
                String path = Environment.getExternalStoragePublicDirectory("") + "/i-YES/";
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String str = dateFormat.format(date);
                String fileName = "i-yes-log-" + str + ".txt";
                File file = new File(path);
                if (!file.exists()) { // 目录
                    file.mkdir();
                }
                File logFile = new File(path + fileName);
                if (!file.exists()) { //日志文件
                    logFile.createNewFile();
                }
                //第三个参数：真，后续内容被追加到文件末尾处，反之则替换掉文件全部内容
                FileWriter fw = new FileWriter(path + fileName, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(logValue + "\r\n");
                bw.close();
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方便插件内部使用的没有返回值的log
     *
     * @param logValue
     */
    public static void writeLogNoCallBack(String logValue) {
        try {
            if (CAN_OUTPUT_LOG) {
                //获取手机本身存储根目录Environment.getExternalStoragePublicDirectory("")
                //sd卡根目录Environment.getExternalStorageDirectory()
                String path = Environment.getExternalStoragePublicDirectory("") + "/i-YES/";
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat dateFormatTwo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeStr = dateFormat.format(date);
                String fileName = "i-yes-log-" + timeStr + ".txt";
                File file = new File(path);
                if (!file.exists()) { // 目录
                    file.mkdir();
                }
                File logFile = new File(path + fileName);
                if (!file.exists()) { //日志文件
                    logFile.createNewFile();
                }
                //第三个参数：真，后续内容被追加到文件末尾处，反之则替换掉文件全部内容
                FileWriter fw = new FileWriter(path + fileName, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(dateFormatTwo.format(new Date()));
                bw.append(logValue + "\r\n");
                bw.close();
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方便插件内部使用的没有返回值的log
     *
     * @param logValue
     */
    public static void writeLogCheckDataLength(String logValue) {
        try {
            if (CAN_OUTPUT_LOG) {
                //获取手机本身存储根目录Environment.getExternalStoragePublicDirectory("")
                //sd卡根目录Environment.getExternalStorageDirectory()
                String path = Environment.getExternalStoragePublicDirectory("") + "/i-YES/";
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat dateFormatTwo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timeStr = dateFormat.format(date);
                String fileName = "i-yes2-log-check-data-length" + timeStr + ".txt";
                File file = new File(path);
                if (!file.exists()) { // 目录
                    file.mkdir();
                }
                File logFile = new File(path + fileName);
                if (!file.exists()) { //日志文件
                    logFile.createNewFile();
                }
                //第三个参数：真，后续内容被追加到文件末尾处，反之则替换掉文件全部内容
                FileWriter fw = new FileWriter(path + fileName, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.append(dateFormatTwo.format(new Date()));
                bw.append(logValue + "\r\n");
                bw.close();
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
