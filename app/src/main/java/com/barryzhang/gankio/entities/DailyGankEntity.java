package com.barryzhang.gankio.entities;

import java.util.List;
import java.util.Map;

/**
 * Created by Barry on 16/4/20.
 */
public class DailyGankEntity extends BaseHttpResponseEntity<Map<String,List<GankItem>>> {
    private List<String> category ;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
}
