package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

/**
 * Created by Barry on 16/4/24.
 */
public abstract class BaseHomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    protected void initToolBar(){



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setSubtitle(R.string.app_main_title);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        Drawable drawable = MaterialDrawableBuilder.with(this)
                .setIcon(keyboardBackspace)
                .setColor(getResColor(R.color.menu_icon))
                .setToActionbarSize()
                .build();
        return drawable;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putStringArrayListExtra("history",history);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            //intent.putStringArrayListExtra("history",history);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
