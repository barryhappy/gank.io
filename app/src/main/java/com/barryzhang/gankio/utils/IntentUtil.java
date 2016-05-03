package com.barryzhang.gankio.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.ui.AboutActivity;
import com.barryzhang.gankio.ui.FavoriteActivity;
import com.barryzhang.gankio.ui.HistoryActivity;
import com.barryzhang.gankio.ui.HtmlActivity;
import com.barryzhang.gankio.ui.MainActivity;

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

}
