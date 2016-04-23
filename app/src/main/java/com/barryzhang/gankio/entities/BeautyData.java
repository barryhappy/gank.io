package com.barryzhang.gankio.entities;

/**
 * Created by Barry on 16/4/21.
 */
public class BeautyData {
    private GankItem beauty;

    private BeautyData(GankItem beauty) {
        this.beauty = beauty;
    }

    public static BeautyData create(GankItem beauty){
        BeautyData b = new BeautyData(beauty);
        return b;
    }

    public GankItem getBeauty() {
        return beauty;
    }

    public void setBeauty(GankItem beauty) {
        this.beauty = beauty;
    }
}
