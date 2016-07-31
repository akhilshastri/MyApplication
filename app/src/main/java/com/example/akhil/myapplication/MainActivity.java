package com.example.akhil.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webview);

        webView.clearCache(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());

                WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);


        webView.loadUrl("http://192.168.0.104:3000");

    }


    public class WebViewClientImpl extends WebViewClient {

        private Activity activity = null;

        public WebViewClientImpl(Activity activity) {
            this.activity = activity;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.indexOf("jenkov.com") > -1 ) return false;

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
            return true;
        }


        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

            if(url.endsWith("app.js")){
                //return loadFromAssets(url, "images/logo.png", "image/png", "");
                return loadFromAssets(url, "www/app.js", "text/javascript", "UTF-8");
            }else {
                return super.shouldInterceptRequest(view, url);
            }

            //return null;
        }

        private WebResourceResponse loadFromAssets( String url,
                                                    String assetPath, String mimeType, String encoding){

            AssetManager assetManager = this.activity.getAssets();
            InputStream input = null;
            try {
                Log.d( "DEBUG" ,  "Loading from assets: " + assetPath);


                input = assetManager.open("www/app.js");
                WebResourceResponse response =
                        new WebResourceResponse("text/javascript", "UTF-8", input);

//                if (url.equals("script_url_to_load_local")) {
//                    return new WebResourceResponse("text/javascript", "UTF-8", new FileInputStream("local_url")));
//                }
                return response;
            } catch (IOException e) {
                Log.e("WEB-APP", "Error loading " + assetPath + " from assets: " +
                        e.getMessage(), e);
            }
            return null;
        }
    }
}
