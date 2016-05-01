package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.barryzhang.gankio.R;

import butterknife.Bind;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.webViewAbout)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_about;
    }

    @Override
    protected void getIntentExtraData(Intent intent) {

    }

    @Override
    protected void afterInject() {

    }

    @Override
    protected void loadData() {
        webView.loadUrl("http://www.barryzhang.com/%E5%85%B3%E4%BA%8E%E5%BC%A0%E6%96%B0%E5%BC%BA");
    }
}
