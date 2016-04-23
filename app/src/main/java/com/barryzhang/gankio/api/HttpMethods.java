package com.barryzhang.gankio.api;

import android.text.TextUtils;

import com.barryzhang.gankio.entities.BeautyData;
import com.barryzhang.gankio.entities.DailyGankEntity;
import com.barryzhang.gankio.entities.GankItem;
import com.barryzhang.gankio.entities.HistoryEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Barry on 16/4/20.
 */
public class HttpMethods {

    public static final String BASE_URL = "http://gank.io/api/";
    public static final int DEFAULT_TIMEOUT = 10;

    Retrofit retrofit ;

    HttpService service ;


    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }


    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(HttpService.class);
    }

    public void getHistory(Subscriber<HistoryEntity> subscriber){
        service.getHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(subscriber);
    }


    public void getDailyGank(Subscriber<List<Object>> subscriber, final String date){
        if(TextUtils.isEmpty(date)){
            subscriber.onError(new RuntimeException("Date format Exception!"));
        }
        String year;
        String month;
        String day;
        try{
            String[] ss = date.split("-");
            year = ss[0];
            month = ss[1];
            day = ss[2];
        }catch (Exception e){
            subscriber.onError(new RuntimeException("Date format Exception!"));
            return;
        }
        service.getDailyGank(year,month,day)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<DailyGankEntity, List<Object>>() {
                    @Override
                    public List<Object> call(DailyGankEntity dailyGankEntity) {
                        List<Object> list = new ArrayList<Object>();
                        list.add(date);
                        //把"福利"放到最前面
                        for(GankItem item : dailyGankEntity.getResults().get("福利")) {
                            list.add(BeautyData.create(item));
                        }
                        for(String key : dailyGankEntity.getCategory()){
                            if("福利".equals(key)){
                                continue;
                            }
                            list.add(key);
                            list.addAll(dailyGankEntity.getResults().get(key));
                        }
                        return list;
                    }
                })
                .subscribe(subscriber);
    }

}

