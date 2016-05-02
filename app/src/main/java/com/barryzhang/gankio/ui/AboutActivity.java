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
import android.webkit.WebView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.utils.IntentUtil;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import butterknife.Bind;
import us.feras.mdv.MarkdownView;

public class AboutActivity extends BaseActivity {
    public static final String GITHUB_URL = "https://github.com/barryhappy/gank.io";

    @Bind(R.id.markDownView)
    MarkdownView markdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        initToolBar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(MaterialDrawableBuilder.with(this)
                .setIcon(MaterialDrawableBuilder.IconValue.GITHUB_CIRCLE)
                .setColor(Color.WHITE)
                .setSizeDp(20)
                .build());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.openUrlWithBrowser(AboutActivity.this,GITHUB_URL);
            }
        });

    }

    @Override
    protected void loadData() {
//        webView.loadUrl("http://www.barryzhang.com/%E5%85%B3%E4%BA%8E%E5%BC%A0%E6%96%B0%E5%BC%BA");
//        webView.setVisibility(View.GONE);
        markdownView.loadMarkdownFile("file:///android_asset/about.md");
    }



    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        setTitle(gank.getWho());
//        toolbar.setSubtitle(gank.getDesc());
//        toolbar.setTitleTextAppearance(this,R.style.TextAppearance_ActionBar_Title);
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
}
