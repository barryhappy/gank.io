package com.barryzhang.gankio.entities;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.io.Serializable;

/**
 * Created by Barry on 16/4/20.
 */
@Table
public class GankItem implements Serializable{
    private Long id;
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public void syncID(){
        id = (long)Math.abs(hashCode());
    }

    public String get_Id() {
        return _id;
    }

    public long getId(){
        return hashCode();
    }

    public void set_Id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GankItem gankItem = (GankItem) o;

        if (desc != null ? !desc.equals(gankItem.desc) : gankItem.desc != null) return false;
        if (type != null ? !type.equals(gankItem.type) : gankItem.type != null) return false;
        if (url != null ? !url.equals(gankItem.url) : gankItem.url != null) return false;
        return who != null ? who.equals(gankItem.who) : gankItem.who == null;

    }

    @Override
    public int hashCode() {
        int result = desc != null ? desc.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (who != null ? who.hashCode() : 0);
        return result;
    }
}
