package com.lichkin.framework.app.android.webview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.lichkin.android.webview.R;
import com.lichkin.framework.app.android.bean.LKWebViewAlertBean;
import com.lichkin.framework.app.android.bean.LKWebViewLogBean;
import com.lichkin.framework.app.android.bean.LKWebViewOpenWinBean;
import com.lichkin.framework.app.android.bean.LKWebViewToastBean;
import com.lichkin.framework.app.android.callback.LKBtnCallback;
import com.lichkin.framework.app.android.utils.LKAlert;
import com.lichkin.framework.app.android.utils.LKLog;
import com.lichkin.framework.app.android.utils.LKToast;
import com.lichkin.framework.app.android.utils.LKWebView;
import com.lichkin.framework.app.android.widget.LKBridgeWebViewWithProgressBar;
import com.lichkin.framework.json.LKJsonUtils;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * 自定义实现
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
class MethodHandler {

    /**
     * 初始化
     * @param url 页面地址
     * @param activity Activity
     * @param container 容器
     */
    static void init(final String url, final Activity activity, final Object container) {
        //JsBridge对象
        final LKBridgeWebViewWithProgressBar webView;
        //loading遮罩对象
        final TextView loadingMask;
        //loading对象
        final AVLoadingIndicatorView loading;

        if (container instanceof View) {
            webView = ((View) container).findViewById(R.id.webview_container);
            loadingMask = ((View) container).findViewById(R.id.loading_mask);
            loading = ((View) container).findViewById(R.id.loading);
        } else if (container instanceof Activity) {
            webView = ((Activity) container).findViewById(R.id.webview_container);
            loadingMask = ((Activity) container).findViewById(R.id.loading_mask);
            loading = ((Activity) container).findViewById(R.id.loading);
        } else {
            return;
        }

        Log.d("LichKin-WebView", "load url: " + url);
        webView.loadUrl(url);

        //禁用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.registerHandler("log", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LKWebViewLogBean bean = LKJsonUtils.toObj(data, LKWebViewLogBean.class);

                String msg = bean.getMsg();
                String info = "WebViewJavascriptBridge -> log -> msg: ";
                if (bean.isJsonMsg()) {
                    bean.setMsg(info + msg.replaceAll("\\\"", "\""));
                } else {
                    bean.setMsg(info + msg);
                }

                LKLog.log("WebView", bean);
            }
        });

        webView.registerHandler("toast", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                LKWebViewToastBean bean = LKJsonUtils.toObj(data, LKWebViewToastBean.class);

                if (bean.isJsonMsg()) {
                    bean.setMsg(bean.getMsg().replaceAll("\\\"", "\""));
                }

                LKToast.toast(activity, bean);
            }
        });

        webView.registerHandler("alert", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                LKWebViewAlertBean bean = LKJsonUtils.toObj(data, LKWebViewAlertBean.class);

                String msg = bean.getMsg();
                if (bean.isJsonMsg()) {
                    bean.setMsg(bean.getMsg().replaceAll("\\\"", "\""));
                }

                LKAlert.alert(activity, bean.getMsg(), new LKBtnCallback() {
                    @Override
                    public void call(Context context, DialogInterface dialog) {
                        if (function != null) {
                            function.onCallBack(null);
                        }
                    }
                });
            }
        });

        webView.registerHandler("showLoading", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                loadingMask.setVisibility(View.VISIBLE);
                loading.show();
            }
        });

        webView.registerHandler("closeLoading", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                loading.hide();
                loadingMask.setVisibility(View.GONE);
            }
        });

        webView.registerHandler("reload", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                webView.reload();
            }
        });

        webView.registerHandler("openWin", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                LKWebViewOpenWinBean bean = LKJsonUtils.toObj(data, LKWebViewOpenWinBean.class);

                LKWebView.open(activity, bean.getUrl());
            }
        });

        webView.registerHandler("closeWin", new BridgeHandler() {
            @Override
            public void handler(final String data, final CallBackFunction function) {
                activity.finish();
            }
        });
    }

}
