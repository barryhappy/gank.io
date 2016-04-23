package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.entities.GankItem;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import butterknife.Bind;

public class HtmlActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;

    private GankItem gank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(gank == null){
            // TODO 处理没有数据的情况
            return;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_html;
    }

    @Override
    protected void getIntentExtraData(Intent intent) {
        gank  = (GankItem) intent.getSerializableExtra("gankItem");
    }

    @Override
    protected void afterInject() {

        initToolBar();

        initWebVIew();
    }

    private void initWebVIew() {
        webView.getSettings().setAppCacheMaxSize(1024*1024*32);
        webView.getSettings().setAppCachePath(getFilesDir().getPath()+"/cache");
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideProgressBar();
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(gank.getWho());
        toolbar.setSubtitle(gank.getDesc());
        toolbar.setTitleTextAppearance(this,R.style.TextAppearance_ActionBar_Title);
//        toolbar.setTitleTextAppearance(this,android.R.style.TextAppearance_DeviceDefault_Widget_ActionBar_Subtitle);
        Drawable drawable = MaterialDrawableBuilder.with(this)
                .setIcon(MaterialDrawableBuilder.IconValue.KEYBOARD_BACKSPACE)
                .setColor(Color.WHITE)
                .setToActionbarSize()
                .build();
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }

    @Override
    protected void loadData() {
        if(gank != null){
            webView.loadUrl(gank.getUrl());
            showProgressBar();
        }
    }

    @Override
    public void onBackPressed() {
        if(webView != null && webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
