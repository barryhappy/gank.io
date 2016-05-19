package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.adapter.DailyGankAdapter;
import com.barryzhang.gankio.api.HttpMethods;
import com.barryzhang.gankio.entities.DaysContent;
import com.barryzhang.gankio.entities.GankItem;
import com.barryzhang.gankio.utils.D;
import com.barryzhang.gankio.utils.IntentUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;
import rx.Subscriber;

public class MainActivity extends BaseHomeActivity {

    private String date;
    private DailyGankAdapter adapterMain;

    @Bind(R.id.listViewMain)
    ListView listViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getIntentExtraData(Intent intent) {
        date = intent .getStringExtra("date");
    }


    @Override
    protected void afterInject() {
        adapterMain = new DailyGankAdapter(this);
        listViewMain.setAdapter(adapterMain);
        setTitle(date);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String newDate = intent.getStringExtra("date");
        if(newDate != null && !date.equals(newDate)){
            date = newDate;
            reloadData();
        }
    }

    @Override
    protected int getCurrentPageMenuIndex() {
        return 0;
    }


    private void reloadData(){
        if(adapterMain != null) {
            adapterMain.clearAndNotify();
        }
        setTitle(date);
        loadData();
    }


    @OnItemClick(R.id.listViewMain)
    void onMainListViewItemClick(int position) {
        if (adapterMain.getData().get(position) instanceof GankItem) {
            Intent intent = new Intent();
            GankItem gankItem = (GankItem) adapterMain.getData().get(position);
            intent.putExtra("gankItem", gankItem);
            IntentUtil.gotoGankDetailActivity(this,intent);
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
        }, date);


        HttpMethods.getInstance().getDaysContent(new Subscriber<DaysContent.Content>() {

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
            public void onNext(DaysContent.Content content) {
                if(content != null){
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    toolbar.setSubtitle(content.getTitle());
                }
            }
        }, date);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            System.exit(0);
        }
    }
}
