package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidadvance.topsnackbar.TSnackbar;
import com.barryzhang.gankio.R;
import com.barryzhang.gankio.dao.DatabaseMethods;
import com.barryzhang.gankio.entities.FavoriteEntity;
import com.barryzhang.gankio.entities.GankItem;
import com.barryzhang.gankio.utils.D;
import com.barryzhang.gankio.utils.IntentUtil;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import butterknife.Bind;
import butterknife.OnClick;

public class HtmlActivity extends BaseActivity {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.viewCover)
    View viewCover;

    private GankItem gank;
    boolean isFavorite = false;

    Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(gank == null){
            D.toast("数据错误！");
            onBackPressed();
            return;
        }
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
        
        refreshFavoriteState();

        setCoverIfVideo();

    }

    private void setCoverIfVideo() {
        if("休息视频".equals(gank.getType())){

            final View barView = getSnackBarView();
            barView.post(new Runnable() {
                @Override
                public void run() {
                    TSnackbar snackBar = TSnackbar.make(barView,
                            "如需播放视频，选择『在浏览器中打开』，进行播放",
                            TSnackbar.LENGTH_INDEFINITE)
                            .setAction("我知道啦", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewCover.setVisibility(View.GONE);
                                }
                            })
                            .setActionTextColor(Color.parseColor("#cccccc"));
                    snackBar.getView().setBackgroundColor(Color.parseColor("#ff4081"));
                    snackBar.show();
                }
            });
            viewCover.setVisibility(View.VISIBLE);
        }else{
            viewCover.setVisibility(View.GONE);
        }
    }

    private void refreshFavoriteState() {
        isFavorite = DatabaseMethods.isFavorite(gank);
        if(isFavorite){
            fab.setImageDrawable(MaterialDrawableBuilder.with(this)
                    .setIcon(MaterialDrawableBuilder.IconValue.STAR)
                    .setColor(Color.WHITE)
                    .setSizeDp(20)
                    .build());
        }else{
            fab.setImageDrawable(MaterialDrawableBuilder.with(this)
                    .setIcon(MaterialDrawableBuilder.IconValue.STAR_OUTLINE)
                    .setColor(Color.WHITE)
                    .setSizeDp(20)
                    .build());
        }
        refreshMenu();

    }

    private void refreshMenu() {
        if(mOptionsMenu != null){
            mOptionsMenu.getItem(0).setVisible(!isFavorite);
            mOptionsMenu.getItem(1).setVisible(isFavorite);
        }
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
        if(viewCover != null && viewCover.isShown()){
            viewCover.setVisibility(View.GONE);
            return ;
        }
        if(webView != null && webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
            IntentUtil.exitLeftRight(this);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.html, menu);
        mOptionsMenu = menu;
        refreshMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_open_in_browser) {
            IntentUtil.openUrlWithBrowser(this,gank.getUrl());
            return true;
        } else if(id == R.id.action_favorite || id == R.id.action_unFavorite) {
           toggleFavorite();
        } else if(id == R.id.action_share){
            if(gank != null) {
                IntentUtil.share(gank.getDesc()
                        .concat(" ").concat(gank.getUrl())
                        .concat(" by ").concat(gank.getWho()));
            }
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.fab)
    public void toggleFavorite(){
        if(isFavorite){
            if(DatabaseMethods.deleteFavorite(FavoriteEntity.newInstance(gank))){
                D.toast("√ 收藏已移除");
            }else{
                D.toast("× 取消收藏失败");
            }
        }else{
            if(DatabaseMethods.saveFavorite(FavoriteEntity.newInstance(gank)) > 0){
                D.toast("√ 已收藏");
            }else{
                D.toast("× 收藏失败");
            }
        }
        refreshFavoriteState();
    }
}
