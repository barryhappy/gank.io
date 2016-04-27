package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.adapter.HistoryAdapter;
import com.barryzhang.gankio.api.DataProvider;
import com.barryzhang.gankio.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnItemClick;

public class HistoryActivity extends BaseHomeActivity {

    @Bind(R.id.listViewHistory)
    ListView listViewHistory;

    List<String> listToShow;
    private List<String> history;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getCurrentPageMenuIndex() {
        return 1;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_history;
    }

    @Override
    protected void getIntentExtraData(Intent intent) {
//        history = intent.getStringArrayListExtra("history");
    }

    @Override
    protected void afterInject() {
        adapter = new HistoryAdapter(this);
        listViewHistory.setAdapter(adapter);
    }


    @OnItemClick(R.id.listViewHistory)
    void onHistoryListClick(int position ){
        if(listToShow != null && listToShow.size() > position){
            if(listToShow.get(position).length() <= 7){
                return; // 月份
            }
            Intent intent = new Intent();
            intent.putExtra("date",listToShow.get(position));
            IntentUtil.gotoMainActivity(this,intent);
        }

    }


    @Override
    protected void loadData() {
        history = DataProvider.getHistory();
        listToShow = new ArrayList<>();
        String month = "";
        for(String date :history){
            String dateMonth = date.substring(0,7);
            if(!month.equals(dateMonth)){
                month = dateMonth;
                listToShow.add(dateMonth);
            }
            listToShow.add(date);
        }
        adapter.addAndNotify(listToShow);
    }
}
