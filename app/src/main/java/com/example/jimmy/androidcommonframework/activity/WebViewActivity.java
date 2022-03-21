package com.example.jimmy.androidcommonframework.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.jimmy.androidcommonframework.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by wangxibin
 * Date: 2022/3/2
 */
public class WebViewActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     */
    private static final String LOG_TAG = "WebViewActivity";
    private WebView mWebView;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_webview);
        mWebView = findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);

        // 下面的一句话是必须的，必须要打开javaScript不然所做一切都是徒劳的
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);


        mWebView.setWebChromeClient(new MyWebChromeClient());

        // 看这里用到了 addJavascriptInterface 这就是我们的重点中的重点
        // 我们再看他的DemoJavaScriptInterface这个类。还要这个类一定要在主线程中
        mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "demoJavaScriptInterface");
        mWebView.addJavascriptInterface(new HelloWorld(), "helloWorld");

        mWebView.loadUrl("file:///android_asset/main.html");
    }

    /**
     *
     */
    class HelloWorld {
        HelloWorld() {

        }

        @JavascriptInterface
        public void show() {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(WebViewActivity.this, HelloWorld.class.getSimpleName(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /**
     * 这是他定义由addJavascriptInterface提供的一个Object
     */
    final class DemoJavaScriptInterface {
        DemoJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * 这不是呼吁界面线程。发表一个运行调用
         * loadUrl on the UI thread.
         * loadUrl在UI线程。
         */
        @JavascriptInterface
        public void clickOnAndroid() {        // 注意这里的名称。它为clickOnAndroid(),注意，注意，严重注意
            mHandler.post(new Runnable() {
                public void run() {
                    // 此处调用 HTML 中的javaScript 函数
                    mWebView.loadUrl("javascript:wave()");
                }
            });
        }
    }

    /**
     * Provides a hook for calling "alert" from javascript. Useful for
     * 从javascript中提供了一个叫“提示框” 。这是很有用的
     * debugging your javascript.
     * 调试你的javascript。
     */
    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d(LOG_TAG, message);
            result.confirm();
            return true;
        }
    }
}
