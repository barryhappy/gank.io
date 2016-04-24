package com.barryzhang.gankio.entities;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Barry on 16/4/19.
 */
public class BaseHttpResponseEntity<T>{
    boolean error;
    T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
