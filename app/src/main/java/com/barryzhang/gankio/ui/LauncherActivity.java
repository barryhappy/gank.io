package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.os.Bundle;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.api.DataProvider;
import com.barryzhang.gankio.api.HttpMethods;
import com.barryzhang.gankio.dao.DatabaseMethods;
import com.barryzhang.gankio.entities.HistoryEntity;
import com.barryzhang.gankio.utils.AppUtil;
import com.barryzhang.gankio.utils.D;
import com.barryzhang.gankio.utils.IntentUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LauncherActivity extends BaseActivity {

    @Bind(R.id.imageViewLauncher)
    SimpleDraweeView imageViewLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void getIntentExtraData(Intent intent) {

    }

    @Override
    protected void afterInject() {
    }

    @Override
    protected void loadData() {
        if(!AppUtil.isNetWork(this)){
            D.toast("没有网络");
            // TODO 弹窗提示网络
        }

        HistoryEntity historyEntity = SugarRecord.findById(HistoryEntity.class,1);
        if(historyEntity == null || historyEntity.getResults() == null){

            loadHistory();
        }


    }


    private void loadHistory() {
        Subscriber<Object> subscriber = new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                D.d("");
            }

            @Override
            public void onError(Throwable e) {
                D.d(e);
            }

            @Override
            public void onNext(Object obj) {
                if(obj instanceof  HistoryEntity) {
                    HistoryEntity historyEntity = (HistoryEntity) obj;
                    if (!historyEntity.isError()) {
                        DatabaseMethods.saveHistory(historyEntity);
                        DataProvider.save(historyEntity.getResults());
                        Intent intent = new Intent();
                        intent.putExtra("date", getLastedDate(historyEntity));
                        IntentUtil.gotoMainActivity(LauncherActivity.this, intent);
                    }
                }else if(obj instanceof Integer){
                    D.toastWhileDebug("超时");
                    Intent intent = new Intent();
                    intent.putExtra("date", getLastedDate(null));
                    IntentUtil.gotoMainActivity(LauncherActivity.this, intent);
                }
                finish();
            }
        };

        Observable<HistoryEntity> observable =
                HttpMethods.getInstance().getService().getHistory();
        Observable.merge(observable, Observable.timer(3, TimeUnit.SECONDS))
                .first()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_launcher;
    }



    private String getLastedDate(HistoryEntity historyEntity) {
        if (historyEntity != null && historyEntity.getResults().size() > 0) {
            return historyEntity.getResults().get(0);
        } else {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                    .format(new Date());
        }
    }
}
