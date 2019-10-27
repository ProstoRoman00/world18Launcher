package ru.world18.a18worldlauncher;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class MyWebViewClient extends WebViewClient {
    public static void overrideSetWevClient(WebView gameWebView) {
        gameWebView.getSettings().setJavaScriptEnabled(true);
        gameWebView.setWebChromeClient(new WebChromeClient());
        gameWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
               /* System.out.println("hello");*/
                return true;
            }
        });
    }
}
