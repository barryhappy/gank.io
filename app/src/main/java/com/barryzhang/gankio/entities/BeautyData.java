package com.barryzhang.gankio.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

/**
 * Created by Barry on 16/4/21.
 */
@Table
public class BeautyData {
    private Long id;
    private String date;
    private GankItem beauty;
    public BeautyData() {
    }
    public BeautyData(String date, GankItem beauty) {
        this.date = date;
        this.beauty = beauty;
    }

    public static BeautyData create(String date, GankItem beauty){
        BeautyData b = new BeautyData(date, beauty);
        return b;
    }

    public GankItem getBeauty() {
        return beauty;
    }

    public void setBeauty(GankItem beauty) {
        this.beauty = beauty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
