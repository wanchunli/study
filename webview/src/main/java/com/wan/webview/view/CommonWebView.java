package com.wan.webview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * webview场景越来越多，架构师的角度
 * 1、要好用、用户体验最重要 a、普通用户 b、程序员
 * 2、高可靠 webview出了问题不影响主进程
 * 3、可扩展 html页面需要经常和native通信，增加功能，放便使用
 * 4、从0开始 在工作中可以用到的模块；一行一行写
 * 5、满足activity + fragment的需求
 * 6、模块化、层次化、组件化、控件化
 */
public class CommonWebView extends WebView {

    public CommonWebView(Context context) {
        super(context);
    }

    public CommonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
