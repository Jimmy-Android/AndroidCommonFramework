package com.example.jimmy.androidcommonframework.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by panchenhuan on 2018/6/11 13:46.
 * <p>
 * panchenhuan@mirahome.me
 */
public class MUtil {

    public static final String YMD = "yyyy-MM-dd";
    public static final String HM = "HH:mm";

    public static int getWindowWidth(Context activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
            return outMetrics.widthPixels;
        }
        return 0;
    }

    public static int getWindowHeight(Context activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
            return outMetrics.heightPixels;
        }
        return 0;
    }

    /**
     * dp转换成px单位
     */
    public static int dp2px(Context ctx, float dp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context ctx, float sp) {
        float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5f);
    }

    /**
     * 判定是否安装微信
     */
    public static boolean isWeChatAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packageManager
        List<PackageInfo> info = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (info != null) {
            for (int i = 0; i < info.size(); i++) {
                String pn = info.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideInputMethod(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 获取文字高度
     *
     * @param textPaint  文字画笔
     * @param baseLine   最底部
     * @param textHeight 文字高度
     * @return 文字的底部位置
     */
    public static float getBaseLine(TextPaint textPaint, float baseLine, float textHeight) {
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float fontHeight = fm.bottom - fm.top;
        return baseLine - (textHeight - fontHeight) / 2 - fm.bottom;
    }

    /**
     * 当前月份的天数
     */
    public static int getDaysOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else if (month == 2) {
            return isLeap(year) ? 29 : 28;
        } else {
            return -1;
        }
    }

    /**
     * 是否为闰年
     */
    public static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * 根据时间格式获取小时和分钟
     *
     * @param timeString 21:30
     * @param pattern    HH:mm
     * @return 小时+分钟
     */
    public static int[] getHourAndMinute(String timeString, String pattern) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat(pattern, Locale.getDefault()).parse(timeString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new int[]{cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE)};
    }

    /**
     * 获取距离开始时间的时间长度
     *
     * @param startHour  开始时间18点
     * @param timeString 21:30
     * @param pattern    HH:mm
     * @return 时长
     */
    public static int getTimeDuration(int startHour, String timeString, String pattern) {
        Calendar cal = Calendar.getInstance();
        int duration = 0;
        try {
            cal.setTime(new SimpleDateFormat(pattern, Locale.getDefault()).parse(timeString));
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            if (hour < startHour) hour += 24;
            duration = cal.get(Calendar.MINUTE) * 60 + (hour - startHour) * 3600;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return duration;
    }

    /**
     * 解析日期中的年、月、日
     *
     * @param date 2017-07-20
     * @return 2017、7、21
     */
    public static int[] getYearMonthDay(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat(YMD, Locale.getDefault()).parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new int[]{cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * 获取手机的ID
     */
    public static String getPhoneDeviceId(Context context) {
        String phoneDeviceId = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            phoneDeviceId = tm.getDeviceId();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(phoneDeviceId)) {
            phoneDeviceId = "35" +
                    Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                    Build.USER.length() % 10; //13 位
        }
        return phoneDeviceId;
    }

    /**
     * 获取版本号
     *
     * @return version code 15
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();//获取包管理器
        try {//获取包信息
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;//返回版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取版本名称
     *
     * @return 版本名称 2.0.0
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();//获取包管理器
        try {  //获取包信息
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当然系统时间
     *
     * @return 按照给定格式返回当前系统时间
     */
    public static String getCurrTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());//设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取昨天的日期
     */
    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return String.format(
                Locale.getDefault(),
                "%04d-%02d-%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH)
        );
    }

    /**
     * 获取当前时区值
     */
    public static float getTimeZoneFloat() {
        int offsetMinutes = TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 60000;
        float zoneFloat = (float) (offsetMinutes / 60);
        float zoneTemp = Math.abs((float) (offsetMinutes % 60));
        if (zoneTemp > 30) {
            zoneFloat = zoneFloat + 1.0f;
        } else if (zoneTemp == 30.0f) {
            zoneFloat = zoneFloat + 0.5f;
        } else if (zoneTemp > 0) {
            zoneFloat = zoneFloat + 0.0f;
        }
        return zoneFloat;
    }

    /**
     * 获取当前时区
     */
    public static String getTimeZoneString() {
        return String.valueOf(TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 1000);
    }

    public static String getOtherTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(false, false, tz.getRawOffset());

    }

    private static String createGmtOffsetString(boolean includeGmt, boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = ' ';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder();
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        builder.append(offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        System.out.println("-------wangzhan------------" + builder.toString() + "----" + offsetMillis + "----" + offsetMinutes);
        return builder.toString().trim();
    }


    /**
     * 判别邮箱 包含aa@qq.vip.com，aa@qq.com
     *
     * @param email 邮箱地址
     */
    public static boolean isEmail(String email) {
        return !TextUtils.isEmpty(email) && email.matches("^[a-zA-Z0-9]+([._\\-]*[a-zA-Z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    }

    /**
     * 判断是否是电话号码
     *
     * @param mobiles 手机号
     */
    public static boolean isMobileNO(String mobiles) {
        return !TextUtils.isEmpty(mobiles) && mobiles.matches("^1(3[0-9]|4[579]|5[0-35-9]|6[6]|7[0135678]|8[0-9]|9[89])\\d{8}$");
    }

    /**
     * 判断密码是否符合
     *
     * @param psw 8-20 任意字符
     */
    public static boolean checkPsw(String psw) {
        return !TextUtils.isEmpty(psw) && psw.matches("^.{8,20}$");
    }

    /**
     * 获取当前App语言
     */
    public static String getAppLanguage(Context context, boolean isChs) {
        Locale locale = context.getResources().getConfiguration().locale;
        switch (locale.getLanguage()) {
            case "en":
                return "en";
            case "de":
                return "de";
//            case "ja":
//                return "jp";
            default:
                return isChs ? "chs" : "zh";
        }
        // return "";
    }

    /**
     * 当前是否是中文环境
     */
    public static boolean isChinaLocal(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return "zh".equals(locale.getLanguage());
    }

    /**
     * 获取URLEncode值
     *
     * @param text 文本
     * @return Encode之后文本
     */
    public static String getURLEncode(String text) {
        if (TextUtils.isEmpty(text)) return null;
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转二进制
     */
    public static String parseString(int value, int length) {
        StringBuilder str = new StringBuilder(Integer.toBinaryString(value));
        while (str.length() < length) str.insert(0, "0");
        return str.toString();
    }

    /**
     * 转十六进制
     */
    public static String parseHexString(int value, int length) {
        StringBuilder str = new StringBuilder(Integer.toHexString(value));
        while (str.length() < length) str.insert(0, "0");
        return str.toString();
    }

    /**
     * 组合成闹钟发送格式
     */
    public static String spliceTimeString(List<String> alarmList) {
        if (alarmList == null || alarmList.isEmpty()) return "";
        StringBuilder sendAlarm = new StringBuilder();
        for (String str : alarmList) {
            sendAlarm.append(str).append(";");
        }
        sendAlarm.append("^");
        return sendAlarm.toString();
    }

    /**
     * 获取设备蓝牙是否可用
     */
    public static boolean getBluetoothEnable() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    /**
     * 打开设备蓝牙
     */
    public static void turnOnBluetooth(Context context) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        // 设置蓝牙可见性，最多300秒
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        context.startActivity(intent);
    }

    /**
     * byte 数组与 int 的相互转换
     */
    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    /**
     * byte 数组与 int 的相互转换
     */
    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    /**
     * byte 与 int 的相互转换
     */
    public static byte intToByte(int x) {
        return (byte) x;
    }

    /**
     * byte 与 int 的相互转换
     */
    public static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }


    /**
     * byte数组转换16进制字符串
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取蓝牙设备睡眠报告文件路径
     */
    public static String getBleReportPath(Context context) {
        return context.getExternalCacheDir() + File.separator + "ble-report" + File.separator + "report" + System.currentTimeMillis() + ".txt";
    }

    /**
     * 获取蓝牙设备固件文件路径
     */
    public static String getBleFirmwarePath(Context context) {
        return context.getExternalCacheDir() + File.separator + "ble-firmware" + File.separator + "firmware.bin";
    }
//  mobsdk

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = getMac(context);

            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMac(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        if (Build.VERSION.SDK_INT < 23) {
            mac = getMacBySystemInterface(context);
        } else {
            mac = getMacByJavaAPI();
            if (TextUtils.isEmpty(mac)) {
                mac = getMacBySystemInterface(context);
            }
        }
        return mac;

    }

    @TargetApi(9)
    private static String getMacByJavaAPI() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                if ("wlan0".equals(netInterface.getName()) || "eth0".equals(netInterface.getName())) {
                    byte[] addr = netInterface.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        return null;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString().toLowerCase(Locale.getDefault());
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            } else {
                return "";
            }
        } catch (Throwable e) {
            return "";
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (context == null) {
            return result;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Throwable e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    /**
     * @param data1
     * @param data2
     * @return data1 与 data2拼接的结果
     */
    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;

    }


    /**
     * 读取本地文件中JSON字符串
     *
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    context.getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
