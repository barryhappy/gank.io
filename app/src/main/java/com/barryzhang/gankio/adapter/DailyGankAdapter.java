package com.barryzhang.gankio.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.entities.BeautyData;
import com.barryzhang.gankio.entities.GankItem;
import com.barryzhang.gankio.ui.view.PinnedSectionListView;
import com.barryzhang.gankio.utils.FrescoImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mzule.easyadapter.TypePerEntityAdapter;
import com.github.mzule.easyadapter.ViewType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Barry on 16/4/20.
 */
public class DailyGankAdapter  extends TypePerEntityAdapter<Object>
        implements PinnedSectionListView.PinnedSectionListAdapter  {

    public DailyGankAdapter(Context context) {
        super(context);
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return getRawViewType(TitleViewType.class) == viewType;
    }

    @Override
    protected void mapEntityViewTypes() {
        mapEntityViewType(String.class,TitleViewType.class);
        mapEntityViewType(GankItem.class,GankItemViewType.class);
        mapEntityViewType(BeautyData.class,BeautyViewType.class);
    }

    public static class TitleViewType extends ViewType<String>{

        @Bind(R.id.textViewTitle)
        TextView textViewTitle;
        @Override
        public void onCreate() {
            setContentView(R.layout.lv_item_title);
            ButterKnife.bind(this, getView());
        }

        @Override
        public void onRender(int position, String data) {
            textViewTitle.setText(data);
        }
    }

    public static class GankItemViewType extends ViewType<GankItem>{
        @Bind(R.id.textViewDesc)
        TextView textViewDesc;
        @Override
        public void onCreate() {
            setContentView(R.layout.lv_item_gank_item);
            ButterKnife.bind(this, getView());
        }

        @Override
        public void onRender(int position, GankItem data) {

            textViewDesc.setText(Html.fromHtml(data.getDesc()+
                    "<font color='#1a1a1a'>（"+data.getWho()+"）</font>"));
        }
    }


    public static class BeautyViewType extends  ViewType<BeautyData>{
        @Bind(R.id.imageViewBeauty)
        SimpleDraweeView imageViewBeauty;
        @Override
        public void onCreate() {
            setContentView(R.layout.lv_item_beautiful);
            ButterKnife.bind(this, getView());
        }

        @Override
        public void onRender(int position, BeautyData data) {
            FrescoImageUtils.loadImage(imageViewBeauty,data.getBeauty().getUrl());
        }
    }
}
