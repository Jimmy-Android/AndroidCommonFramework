package com.example.jimmy.androidcommonframework.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Toast图文
 */
public class ShowToast {

    // private static Toast toast;
    //
    // @SuppressLint("ShowToast")
    // public static void show(Object info) {
    //     if (info == null) {
    //         return;
    //     }
    //     if (TextUtils.isEmpty(info.toString())) {
    //         return;
    //     }
    //     if (toast == null) {
    //         toast = Toast.makeText(TUIConfig.getAppContext(), info.toString(), Toast.LENGTH_SHORT);
    //     } else {
    //         toast.setText(info.toString());
    //     }
    //     toast.show();
    // }
    //
    // public static void cancel() {
    //     if (toast != null) {
    //         toast.cancel();
    //     }
    // }
    //
    // public static void showImageToast(Context context, String message, int resId) {
    //     toast = new Toast(context);
    //     LayoutInflater inflater = LayoutInflater.from(context);
    //     View view = inflater.inflate(R.layout.view_toast_save_image, null);
    //     TextView tv = (TextView) view.findViewById(R.id.tv_toast);
    //     ImageView mImgShow = (ImageView) view.findViewById(R.id.iv_toast);
    //     if (message != null) {
    //         tv.setText(message);
    //     }
    //     mImgShow.setImageResource(resId);
    //     toast.setView(view);
    //     toast.setGravity(Gravity.CENTER, 0, 0);
    //     toast.setDuration(Toast.LENGTH_SHORT);
    //     toast.show();
    // }
    //
    // public static void showToastWithImageAndText(Context context, CharSequence message, int iconResId, int duration) {
    //     if (toast == null) {
    //         toast = Toast.makeText(context, message, duration);
    //     } else {
    //         toast.setText(message);
    //     }
    //     toast.setGravity(Gravity.CENTER, 0, 0);
    //     LinearLayout toastView = (LinearLayout) toast.getView();
    //     ImageView imageView = new ImageView(context);
    //     imageView.setImageResource(iconResId);
    //     toastView.addView(imageView, 0);
    //     toast.show();
    // }

}
