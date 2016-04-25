package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.adapter.HistoryAdapter;
import com.barryzhang.gankio.api.DataProvider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class HistoryActivity extends BaseHomeActivity {

    @Bind(R.id.listViewHistory)
    ListView listViewHistory;

    private List<String> history;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected void loadData() {
        history = DataProvider.getHistory();
        List<String> list = new ArrayList<>();
        String month = "";
        for(String date :history){
            String dateMonth = date.substring(0,7);
            if(!month.equals(dateMonth)){
                month = dateMonth;
                list.add(dateMonth);
            }
            list.add(date);
        }
        adapter.addAndNotify(list);
    }
}
