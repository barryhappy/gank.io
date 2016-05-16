package com.barryzhang.gankio.api;

import com.barryzhang.gankio.entities.DailyGankEntity;
import com.barryzhang.gankio.entities.DaysContent;
import com.barryzhang.gankio.entities.HistoryEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Barry on 16/4/20.
 */
public interface HttpService {

    @GET("day/history")
    Observable<HistoryEntity> getHistory();


    @GET("day/{year}/{month}/{day}")
    Observable<DailyGankEntity> getDailyGank(
            @Path("year")String year,
            @Path("month") String month,
            @Path("day") String day
    );

    @GET("history/content/day/{year}/{month}/{day}")
    Observable<DaysContent> getDaysContent(
            @Path("year")String year,
            @Path("month") String month,
            @Path("day") String day
    );


}
