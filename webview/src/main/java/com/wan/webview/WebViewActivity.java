package com.wan.webview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.webkit.WebChromeClient;

import com.wan.webview.databinding.ActivityWebViewBinding;

/**
 * autoservice
 */
public class WebViewActivity extends AppCompatActivity {

    ActivityWebViewBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(WebViewActivity.this,R.layout.activity_web_view);
//        viewBinding.webview.setWebChromeClient(new WebChromeClient());
        viewBinding.webview.getSettings().setJavaScriptEnabled(true);
        viewBinding.webview.loadUrl("https://www.baidu.com");
    }
}