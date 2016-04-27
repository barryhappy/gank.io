package com.barryzhang.gankio.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

import com.barryzhang.gankio.R;
import com.barryzhang.gankio.entities.BeautyData;
import com.barryzhang.gankio.entities.FavoriteEntity;
import com.barryzhang.gankio.entities.GankItem;
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
public class FavoriteAdapter extends TypePerEntityAdapter<Object>
        implements PinnedSectionListView.PinnedSectionListAdapter  {

    public FavoriteAdapter(Context context) {
        super(context);
    }

    @Override
    protected void mapEntityViewTypes() {

        mapEntityViewType(String.class,DailyGankAdapter.TitleViewType.class);
        mapEntityViewType(FavoriteEntity.class,FavoriteViewType.class);
    }


    public static class FavoriteViewType extends ViewType<FavoriteEntity>{
        @Bind(R.id.textViewDesc)
        TextView textViewDesc;
        @Override
        public void onCreate() {
            setContentView(R.layout.lv_item_gank_item);
            ButterKnife.bind(this, getView());
        }

        @Override
        public void onRender(int position, FavoriteEntity data) {

            textViewDesc.setText(Html.fromHtml(data.getData().getDesc()+
                    "<font color='#222'>（"+data.getData().getWho()+"）</font>"));
        }
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return getRawViewType(DailyGankAdapter.TitleViewType.class) == viewType;
    }
}
