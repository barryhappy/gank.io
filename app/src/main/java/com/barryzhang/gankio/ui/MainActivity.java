package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.adapter.DailyGankAdapter;
import com.barryzhang.gankio.api.HttpMethods;
import com.barryzhang.gankio.entities.BeautyData;
import com.barryzhang.gankio.entities.DailyGankEntity;
import com.barryzhang.gankio.entities.GankItem;
import com.barryzhang.gankio.entities.HistoryEntity;
import com.barryzhang.gankio.utils.D;
import com.pnikosis.materialishprogress.ProgressWheel;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import rx.Subscriber;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private HistoryEntity historyEntity;
    private List<String> history;
    private DailyGankAdapter adapterMain;

    @Bind(R.id.listViewMain)
    ListView listViewMain;

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setMenu(navigationView,0, MaterialDrawableBuilder.IconValue.CALENDAR,"Item 1");
        setMenu(navigationView,1, MaterialDrawableBuilder.IconValue.APPLE_FINDER,"Item 2");
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setMenu(NavigationView navigationView,int index ,
                         MaterialDrawableBuilder.IconValue keyboardBackspace, String text) {
        navigationView.getMenu().getItem(index).setIcon(getDrawable(keyboardBackspace));
        navigationView.getMenu().getItem(index).setTitle(text);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getIntentExtraData(Intent intent) {
        historyEntity = (HistoryEntity) intent.getSerializableExtra("historyEntity");
        history = intent.getStringArrayListExtra("history");
    }


    @Override
    protected void afterInject() {
        adapterMain = new DailyGankAdapter(this);
        listViewMain.setAdapter(adapterMain);
    }


    @OnItemClick(R.id.listViewMain)
    void onMainListViewItemClick(int position) {
        if(adapterMain.getData().get(position) instanceof GankItem) {
            Intent intent = new Intent(this, HtmlActivity.class);
            GankItem gankItem = (GankItem)adapterMain.getData().get(position);
            intent.putExtra("gankItem",gankItem);
            startActivity(intent);
        }
    }

    @Override
    protected void loadData() {
        HttpMethods.getInstance().getDailyGank(new Subscriber<List<Object>>() {

            @Override
            public void onStart() {
                super.onStart();
                showProgressBar();
            }

            @Override
            public void onCompleted() {
                hideProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                D.d(e.getMessage());
                hideProgressBar();
            }

            @Override
            public void onNext(List<Object> list) {
                adapterMain.addAndNotify(list);
            }
        }, getLastedDate());
    }

    private String getLastedDate() {
        if(history != null && history.size()>0) {
            return history.get(0);
        }else{
            return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                    .format(new Date());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Drawable getDrawable(MaterialDrawableBuilder.IconValue keyboardBackspace){
        Drawable drawable = MaterialDrawableBuilder.with(this)
                .setIcon(keyboardBackspace)
                .setColor(getResColor(R.color.menu_icon))
                .setToActionbarSize()
                .build();
        return drawable;
    }
}
