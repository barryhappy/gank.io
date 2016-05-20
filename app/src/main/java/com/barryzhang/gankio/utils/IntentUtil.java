package com.barryzhang.gankio.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.application.MyApp;
import com.barryzhang.gankio.ui.AboutActivity;
import com.barryzhang.gankio.ui.FavoriteActivity;
import com.barryzhang.gankio.ui.HistoryActivity;
import com.barryzhang.gankio.ui.HtmlActivity;
import com.barryzhang.gankio.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Barry on 16/4/25.
 */
public class IntentUtil {

    public static void gotoMainActivity(Activity activity ,Intent data){
        if(activity instanceof  MainActivity){
            return;
        }
        Intent intent = new Intent(activity, MainActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        activity.startActivity(intent);
        enterOutIn(activity);
    }



    public static void gotoHistoryActivity(Activity activity ,Intent data){
        if(activity instanceof HistoryActivity){
            return;
        }
        Intent intent = new Intent(activity, HistoryActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        activity.startActivity(intent);
        enterOutIn(activity);
    }



    public static void gotoFavoriteActivity(Activity activity ,Intent data){
        if(activity instanceof FavoriteActivity){
            return;
        }
        Intent intent = new Intent(activity, FavoriteActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        activity.startActivity(intent);
        enterOutIn(activity);
    }

    public static void gotoGankDetailActivity(Activity activity, Intent data){
        if(activity instanceof HtmlActivity){
            return;
        }
        Intent intent = new Intent(activity,HtmlActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        activity.startActivity(intent);
        enterLeftRight(activity);
    }

    public static void gotoAboutActivity(Activity activity, Intent data){
        if(activity instanceof AboutActivity){
            return;
        }
        Intent intent = new Intent(activity,AboutActivity.class);
        if(data != null){
            intent.putExtras(data);
        }
        activity.startActivity(intent);
        enterLeftRight(activity);
    }

    public static void openUrlWithBrowser(Activity act,String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        act.startActivity(intent);
    }


    public static void exitLeftRight(Activity activity){
        activity.overridePendingTransition(
                R.anim.fade_left_to_middle,
                R.anim.fade_middle_to_right);
    }


    public static void enterOutIn(Activity activity) {
        activity.overridePendingTransition(
                R.anim.fade_alpha_half_one,
                R.anim.fade_alpha_one_half);
    }


    public static void exitOutIn(Activity activity) {
        activity.overridePendingTransition(
                R.anim.fade_alpha_half_one,
                R.anim.fade_alpha_one_half);
    }



    public static void enterLeftRight(Activity activity){
        activity.overridePendingTransition(
                R.anim.fade_right_to_middle,
                R.anim.fade_middle_to_left);
    }


    /**
     * 分享
     * @param content
     * @return
     */
    public static boolean share(String content){

        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            List<ResolveInfo> resInfo = MyApp.getInstance().getPackageManager().queryIntentActivities(intent, 0);
            if (resInfo != null) {
                List<Intent> targetedShareIntents = new ArrayList<Intent>();
                for (ResolveInfo info : resInfo) {
                    Intent targeted = new Intent(Intent.ACTION_SEND);
                    targeted.setType("text/plain");
                    ActivityInfo activityInfo = info.activityInfo;
                    targeted.putExtra(Intent.EXTRA_TEXT, content);
                    targeted.setPackage(activityInfo.packageName);
                    targetedShareIntents.add(targeted);
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                try {
                    chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApp.getInstance().startActivity(chooserIntent);
                    return true;
                } catch (android.content.ActivityNotFoundException ex) {
                    D.toastWhileDebug(MyApp.getInstance(), "Can't find sharecomponent to share");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            D.toastWhileDebug("ERROR!\n打开分享错误！");
        }
        return false;
    }

}
