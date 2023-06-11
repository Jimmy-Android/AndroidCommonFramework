package com.example.jimmy.androidcommonframework.application;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxibin
 * Date: 2022/3/11
 */
public class MainApplication extends Application {

    private MainApplication instance;
    private List<Activity> activities;

    /**
     * 单例模式
     *
     * @return
     */
    public MainApplication getInstance() {
        if (instance == null)
            return new MainApplication();
        return instance;
    }

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 遍历所有Activity并finish
     */
    public void finishActivity() {
        for (Activity activity : activities) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 在创建应用程序时调用，可以重写这个方法来实例化应用程序单态，以及创建和实例化任何应用
     * 程序状态变量或共享资源
     */
    @Override
    public void onCreate() {
        super.onCreate();
        catchCrashLog();
    }

    /**
     * 作为onLowMemory的一个特定于应用程序的替代选择，在android4.0时引入，
     * 在程序运行时决定当前应用程序应该尝试减少其内存开销时（通常在它进入后台时）调用
     * 它包含一个level参数，用于提供请求的上下文
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 与Activity不同，在配置改变时，应用程序对象不会被终止和重启。
     * 如果应用程序使用的值依赖于特定的配置，则重写这个方法来重新加载这些值，或者在应用程序级别处理这些值的改变
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 当系统处于资源匮乏时，具有良好行为的应用程序可以释放额外的内存。
     * 这个方法一般只会在后台进程已经终止，但是前台应用程序仍然缺少内存时调用。
     * 我们可以重写这个程序来清空缓存或者释放不必要的资源
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 捕捉App的崩溃日志
     */
    private void catchCrashLog() {
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                try {
                    // 获取崩溃时的UNIX时间戳
                    long timeMillis = System.currentTimeMillis();
                    // 将时间戳转换成能看懂的格式
                    StringBuilder stringBuilder = new StringBuilder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis)));
                    // 获取错误信息
                    stringBuilder.append(throwable.getMessage());
                    stringBuilder.append("\n");
                    // 获取堆栈信息
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    throwable.printStackTrace(pw);
                    stringBuilder.append(sw.toString());
                    // 这就是完整的错误信息，保存到本地
                    String crashLog = stringBuilder.toString();
                    // 获取手机本身存储根目录Environment.getExternalStoragePublicDirectory("")
                    // sd卡根目录Environment.getExternalStorageDirectory()
                    String path = Environment.getExternalStoragePublicDirectory("") + "/IEZ/";
                    String fileName = "iez-crash-log.txt";
                    File file = new File(path);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File logFile = new File(path + fileName);
                    if (!file.exists()) { //日志文件
                        logFile.createNewFile();
                    }
                    // 第三个参数：真，后续内容被追加到文件末尾处，反之则替换掉文件全部内容
                    FileWriter fw = new FileWriter(path + fileName, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.append(crashLog + "\r\n");
                    bw.close();
                    fw.close();
                    // 最后处理这个崩溃，这里使用默认的处理方式让APP停止运行
                    defaultHandler.uncaughtException(thread, throwable);
                } catch (Exception e) {
                    e.printStackTrace();
                    // 最后处理这个崩溃，这里使用默认的处理方式让APP停止运行
                    defaultHandler.uncaughtException(thread, throwable);
                }
            }
        });
    }
}
