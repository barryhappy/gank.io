package com.barryzhang.gankio.entities;

import android.os.Bundle;

import com.barryzhang.gankio.utils.D;
import com.orm.dsl.Table;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Barry on 16/4/27.
 */
@Table
public class FavoriteEntity {
    private Long id;


    private String type;
    private long addTime;
    private GankItem data;


    public FavoriteEntity() {
    }


    public static FavoriteEntity newInstance(GankItem data) {
        if(data == null){
            D.toastWhileDebug("Oh~ NULL!");
            return null;
        }
        FavoriteEntity e = new FavoriteEntity();
        e.setData(data);
        e.setAddTime(Calendar.getInstance().getTimeInMillis());
        e.setType(data.getType());
        e.setId((long)Math.abs(e.hashCode()));
        return e;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FavoriteEntity that = (FavoriteEntity) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public GankItem getData() {
        return data;
    }

    public void setData(GankItem data) {
        this.data = data;
    }
}
