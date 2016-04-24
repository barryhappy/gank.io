package com.barryzhang.gankio.api;

import com.barryzhang.gankio.entities.HistoryEntity;
import com.barryzhang.gankio.utils.UtilShare;

import java.util.List;

/**
 * Created by Barry on 16/4/24.
 */
public class DataProvider {
    private static List<String> history;
    public synchronized static List<String> getHistory(){
        if(history == null){
            history = UtilShare.getDataStringList("history");
        }
        return history;
    }
    public static void save(List<String> history){
        DataProvider.history = history;
        UtilShare.saveData("history",history);
    }
}
