package com.barryzhang.gankio.entities;

import com.orm.dsl.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barry on 16/4/20.
 */
@Table
public class HistoryEntity
        extends BaseHttpResponseEntity<ArrayList<String>> implements Serializable{
    private static final long serialVersionUID = 3661921125202914678L;
    private Long id;

    private String list;

    public HistoryEntity() {
    }

    public HistoryEntity(Long id, ArrayList<String> results) {
        this.id = id;
        this.results = results;
    }


}
