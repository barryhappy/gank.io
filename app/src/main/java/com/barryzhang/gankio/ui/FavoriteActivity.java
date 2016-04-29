package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.adapter.FavoriteAdapter;
import com.barryzhang.gankio.dao.DatabaseMethods;
import com.barryzhang.gankio.entities.FavoriteEntity;
import com.barryzhang.gankio.entities.GankItem;
import com.barryzhang.gankio.utils.IntentUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;

public class FavoriteActivity extends BaseHomeActivity {


    @Bind(R.id.listViewFavorite)
    ListView listView;

    List<FavoriteEntity> all;

    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_favorite;
    }

    @Override
    protected void getIntentExtraData(Intent intent) {

    }

    @Override
    protected void afterInject() {
        adapter = new FavoriteAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        all = DatabaseMethods.findAllFavorite();
        if(all.isEmpty()){
            return ;
        }
        adapter.addAndNotify(all);
    }


    @OnItemClick(R.id.listViewFavorite)
    void onListViewItemClick(int position){
        Intent intent = new Intent();
        GankItem gankItem = all.get(position).getData();
        intent.putExtra("gankItem", gankItem);
        IntentUtil.gotoGankDetailActivity(this,intent);
    }

    @Override
    protected int getCurrentPageMenuIndex() {
        return 2;
    }
}
