package com.barryzhang.gankio.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.ButterKnife;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.ui.view.LoadingDialog;
import com.barryzhang.gankio.utils.Tinter;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by Barry on 16/4/19.
 */
public abstract class BaseActivity extends AppCompatActivity {

    LoadingDialog loadingDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        getIntentExtraData(getIntent());
        ButterKnife.bind(this);
        Tinter.enableIfSupport(this);
        afterInject();
        loadData();
    }

    public void back() {
        onBackPressed();
    }

    protected abstract int getLayoutResourceId();

    protected abstract void getIntentExtraData(Intent intent);

    protected abstract void afterInject();

    protected abstract void loadData();


    private synchronized void createDialog(){
        loadingDialog = LoadingDialog.createDialog(this);
    }

    public void showProgressBar(){
        if(loadingDialog == null){
            createDialog();
        }
        loadingDialog.show();

    }

    public void hideProgressBar(){
        if(loadingDialog != null){
            loadingDialog.dismiss();
        }
    }

    public int getResColor(int colorID){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getColor(colorID);
        }else {
            return getResources().getColor(colorID);
        }
    }
}
