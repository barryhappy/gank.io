package com.barryzhang.gankio.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.entities.BeautyData;
import com.barryzhang.gankio.ui.view.PinnedSectionListView;
import com.barryzhang.gankio.utils.FrescoImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.mzule.easyadapter.MultiTypeAdapter;
import com.github.mzule.easyadapter.TypePerEntityAdapter;
import com.github.mzule.easyadapter.ViewType;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Barry on 16/4/20.
 */
public class HistoryAdapter extends MultiTypeAdapter<String>
        implements PinnedSectionListView.PinnedSectionListAdapter  {

    public HistoryAdapter(Context context) {
        super(context);
    }

    @Override
    protected void registerViewTypes() {

        registerViewType(HistoryTitleViewType.class);
        registerViewType(HistoryItemViewType.class);
    }

    @Override
    protected Class<? extends ViewType> getViewType(int position, String data) {
        return data.length() > 7 ? HistoryItemViewType.class : HistoryTitleViewType.class;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return getRawViewType(HistoryTitleViewType.class) == viewType;
    }

    public static class HistoryTitleViewType extends ViewType<String>{

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

    public static class HistoryItemViewType extends ViewType<String>{
        @Bind(R.id.textViewDesc)
        TextView textViewDesc;
        @Override
        public void onCreate() {
            setContentView(R.layout.lv_item_gank_item);
            ButterKnife.bind(this, getView());
        }

        @Override
        public void onRender(int position, String data) {

            textViewDesc.setText(Html.fromHtml("<font color='#1a1a1a'>"+data+"</font>"));
//            textViewDesc.setText(data);
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
