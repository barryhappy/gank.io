package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.dao.DatabaseMethods;
import com.barryzhang.gankio.entities.FavoriteEntity;
import com.barryzhang.gankio.utils.IntentUtil;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Barry on 16/4/24.
 */
public abstract class BaseHomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    FloatingActionButton fab;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    protected void initToolBar() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setSubtitle(R.string.app_main_title);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSnackMessage();
                }
            });
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.closeDrawers();
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setMenu(navigationView, 0, MaterialDrawableBuilder.IconValue.HOME);
        setMenu(navigationView, 1, MaterialDrawableBuilder.IconValue.HISTORY);
        setMenu(navigationView, 2, MaterialDrawableBuilder.IconValue.STAR);
        navigationView.setNavigationItemSelectedListener(this);

    }



    private void setMenu(NavigationView navigationView, int index,
                         MaterialDrawableBuilder.IconValue keyboardBackspace) {
        navigationView.getMenu().getItem(index).setIcon(getDrawable(keyboardBackspace));
    }

    private Drawable getDrawable(MaterialDrawableBuilder.IconValue keyboardBackspace) {
        return MaterialDrawableBuilder.with(this)
                .setIcon(keyboardBackspace)
                .setColor(getResColor(R.color.menu_icon))
                .setToActionbarSize()
                .build();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {

        final int id = item.getItemId();
        Runnable task = new Runnable() {
            @Override
            public void run() {

                if (id == R.id.nav_home) {
                    IntentUtil.gotoMainActivity(BaseHomeActivity.this, null);
                } else if (id == R.id.nav_history) {
                    IntentUtil.gotoHistoryActivity(BaseHomeActivity.this, null);

                } else if (id == R.id.nav_favorite){
                    IntentUtil.gotoFavoriteActivity(BaseHomeActivity.this, null);

                } else if (id == R.id.nav_manage) {

                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_about) {
                    IntentUtil.gotoAboutActivity(BaseHomeActivity.this,null);
                }

            }
        };

        //在抽屉关闭之后执行任务
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            drawer.post(task);
        }else{
            drawer.postDelayed(task,200);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(navigationView != null && navigationView.getMenu().size() > getCurrentPageMenuIndex()) {
            navigationView.getMenu().getItem(getCurrentPageMenuIndex()).setChecked(true);
        }
    }


    private void showSnackMessage() {
        View barView = getSnackBarView();
        Snackbar.make(barView, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);
                        fab.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {
                        super.onShown(snackbar);
                        fab.setVisibility(View.GONE);
                    }
                })
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).show();
    }


    //当前页面在侧边栏menu中的位置
    abstract protected int getCurrentPageMenuIndex();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentUtil.exitOutIn(this);
    }
}
