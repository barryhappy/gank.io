package com.barryzhang.gankio.dao;

import com.barryzhang.gankio.entities.BeautyData;
import com.barryzhang.gankio.entities.FavoriteEntity;
import com.barryzhang.gankio.entities.GankItem;
import com.barryzhang.gankio.entities.HistoryEntity;
import com.barryzhang.gankio.utils.D;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Barry on 16/4/20.
 */
public class
DatabaseMethods {
    public static void saveHistory(HistoryEntity historyEntity){
        //TODO 存储历史
        //SugarRecord.save(historyEntity);
    }
    public static void saveBeautiy(BeautyData beautyData){
        //TODO 保存福利
    }

    /**
     *
     * @param favoriteEntity
     * @return 返回值大于0表示存储成功
     */
    public static long saveFavorite(FavoriteEntity favoriteEntity){
        if(favoriteEntity == null){
            D.toastWhileDebug("Save favoriteEntity FAILED!");
            return -1;
        }
        favoriteEntity.getData().syncID();
        if(SugarRecord.save(favoriteEntity.getData()) > 0) {
            return SugarRecord.save(favoriteEntity);
        }
        return 0;
    }

    public static boolean deleteFavorite(FavoriteEntity favoriteEntity) {
        if(favoriteEntity == null){
            return false;
        }
        return SugarRecord.delete(favoriteEntity);
    }

    public static List<FavoriteEntity>  findAllFavorite(){
        Iterator<FavoriteEntity> iterator = SugarRecord.findAll(FavoriteEntity.class);
        List<FavoriteEntity> list = new ArrayList<>();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }

    public static boolean isFavorite(GankItem item){
        if(item == null){
            return false;
        }
        FavoriteEntity favoriteEntity = FavoriteEntity.newInstance(item);
        long count = 0;
        if (favoriteEntity != null) {
            count = SugarRecord.count(
                    FavoriteEntity.class,"id = ?",
                    new String[]{String.valueOf(favoriteEntity.getId())});
        }
        return count > 0;
    }
}
