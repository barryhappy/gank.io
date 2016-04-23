package com.barryzhang.gankio.api;

import com.barryzhang.gankio.entities.HistoryEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Barry on 16/4/19.
 */
public interface HistoryService {
//    @GET("day/history")
//    Call<HistoryEntity> getHistory();

    @GET("day/history")
    Observable<HistoryEntity> getRxHistory();
}
