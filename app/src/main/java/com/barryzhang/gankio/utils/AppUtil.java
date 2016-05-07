package com.barryzhang.gankio.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Barry on 16/5/7.
 */
public class AppUtil {

    /**
     * 判断是否有网络
     * @param context
     * @return
     */
    public static boolean isNetWork(Context context) {
        ConnectivityManager cwjManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        return false;
    }
}
