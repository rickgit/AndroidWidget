package edu.ptu.demo.test.webview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import edu.ptu.demo.R;


/**
 * Created by wuer on 16/9/21.
 */
public class FeedbackFragmentActivity extends FragmentActivity{

    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.feedback_activity_fragment);



        webView = (WebView) findViewById(R.id.myWebView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        // 下面的一句话是必须的，必须要打开javaScript不然所做一切都是徒劳的
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        webView.setWebChromeClient(new MyWebChromeClient());

        // 看这里用到了 addJavascriptInterface 这就是我们的重点中的重点
        // 我们再看他的DemoJavaScriptInterface这个类。还要这个类一定要在主线程中
        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");
        webView.loadUrl("file:///android_asset/test.html");
    }
    // 这是他定义由 addJavascriptInterface 提供的一个Object
    final class DemoJavaScriptInterface {
        DemoJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * 这不是呼吁界面线程。发表一个运行调用
         * loadUrl on the UI thread.
         * loadUrl在UI线程。
         */
//        @JavascriptInterface
        public void clickOnAndroid() {        // 注意这里的名称。它为clickOnAndroid(),注意，注意，严重注意
            new Handler(getMainLooper()).post(new Runnable() {
                public void run() {
                    // 此处调用 HTML 中的javaScript 函数
                    webView.loadUrl("javascript:wave()");
                    Toast.makeText(getApplicationContext(),"执行clickOnAndroid方法",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
// 线下的代码可以不看，调试用的
///////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Provides a hook for calling "alert" from javascript. Useful for
     * 从javascript中提供了一个叫“提示框” 。这是很有用的
     * debugging your javascript.
     *  调试你的javascript。
     */
    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }
    }
}
