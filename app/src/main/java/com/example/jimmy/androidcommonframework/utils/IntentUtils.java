package com.example.jimmy.androidcommonframework.utils;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.jimmy.androidcommonframework.activity.WelcomeActivity;

/**
 * Created by Jimmy on 2018/6/12.
 * 主要负责Activity或者Fragment等跳转
 */

public class IntentUtils {

    /**
     * 跳转到手机外部的浏览器并可以选择用哪个浏览器打开
     */
    public static Intent toSystemBrowser(String redirectUrl) {
        Uri uri = Uri.parse(redirectUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //intent.setAction(Intent.ACTION_VIEW);
        //intent.setData(uri);
        return intent;
    }

    /**
     * 指定跳转到某个浏览器
     */
    public static Intent toOtherBrowser(String url, String packageUrl, String contextUrl) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //打开UC浏览器
        //intent.setClassName("com.UCMobile","com.uc.browser.InnerUCMobile");
        //打开QQ浏览器
        //intent.setClassName("com.tencent.mtt","com.tencent.mtt.MainActivity");
        intent.setClassName(packageUrl, contextUrl);
        return intent;
    }

    public static Intent toWelcomeActivity(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        return intent;
    }
}
