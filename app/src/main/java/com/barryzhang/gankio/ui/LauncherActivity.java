package com.barryzhang.gankio.ui;

import android.content.Intent;
import android.os.Bundle;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.api.HistoryService;
import com.barryzhang.gankio.api.HttpMethods;
import com.barryzhang.gankio.api.HttpService;
import com.barryzhang.gankio.dao.DatabaseMethods;
import com.barryzhang.gankio.entities.HistoryEntity;
import com.barryzhang.gankio.utils.D;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.barryzhang.gankio.utils.FrescoImageUtils;

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
        FrescoImageUtils.loadImage(imageViewLauncher,
                "http://m2.quanjing.com/2m/ibrm048/iblsab01238174.jpg");
    }

    @Override
    protected void loadData() {
        loadThree();
    }

    private void loadOne() {
        //        Retrofit retrofit = new Retrofit.Builder()
        //                .baseUrl("http://gank.io/api/")
        //                .addConverterFactory(GsonConverterFactory.create())
        //                .build();
        //        HistoryService service = retrofit.create(HistoryService.class);
        //        Call<HistoryEntity> call = service.getHistory();
        //        call.enqueue(new Callback<HistoryEntity>(){
        //
        //            @Override
        //            public void onResponse(Call<HistoryEntity> call, Response<HistoryEntity> response) {
        //
        //                if(response != null){
        //                    response.body().getResults();
        //                }
        //            }
        //
        //            @Override
        //            public void onFailure(Call<HistoryEntity> call, Throwable t) {
        //
        //            }
        //        });
    }

    private void loadTwo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpMethods.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        HistoryService service = retrofit.create(HistoryService.class);
        Observable<HistoryEntity> observable = service.getRxHistory();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HistoryEntity>() {
                    @Override
                    public void onCompleted() {
                        D.d("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        D.d(e);
                    }

                    @Override
                    public void onNext(HistoryEntity historyEntity) {
                        D.d(historyEntity.isError());
                    }
                });

    }


    private void loadThree() {
        HttpMethods.getInstance().getHistory(new Subscriber<HistoryEntity>() {
            @Override
            public void onCompleted() {
                D.d("");
            }

            @Override
            public void onError(Throwable e) {
                D.d(e);
            }

            @Override
            public void onNext(HistoryEntity historyEntity) {
                if(!historyEntity.isError()) {
                    DatabaseMethods.saveHistory(historyEntity);

                    Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                    intent.putExtra("historyEntity", historyEntity);
                    intent.putExtra("history", historyEntity.getResults());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_launcher;
    }

}
