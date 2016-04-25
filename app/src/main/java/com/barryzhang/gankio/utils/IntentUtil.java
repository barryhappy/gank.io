package com.barryzhang.gankio.utils;

import android.app.Activity;
import android.content.Intent;

import com.barryzhang.gankio.ui.HistoryActivity;
import com.barryzhang.gankio.ui.MainActivity;

/**
 * Created by Barry on 16/4/25.
 */
public class IntentUtil {

    public static void gotoMainActivity(Activity activity ){
        if(activity instanceof  MainActivity){
            return;
        }
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }



    public static void gotoHistoryActivity(Activity activity ){
        if(activity instanceof HistoryActivity){
            return;
        }
        Intent intent = new Intent(activity, HistoryActivity.class);
        activity.startActivity(intent);
    }

}
