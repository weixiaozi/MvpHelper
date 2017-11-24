package mvphelper.qiang.com.mvphelper.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.domin.CollectionBean;
import mvphelper.qiang.com.mvphelper.utils.ImageLoader;

/**
 * Created by dell on 2017/11/23.
 */

public class Test2Adapter extends BaseQuickAdapter<CollectionBean.ListEntity, BaseViewHolder> {
    public Test2Adapter(int layoutResId, @Nullable List<CollectionBean.ListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionBean.ListEntity item) {
        new ImageLoader().setUrl(item.getPoster()).setLoadDrawable(R.drawable.leak_canary_icon).setmCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) helper.getView(R.id.item_iv)).build();
        helper.setText(R.id.item_txt, item.getVideoID());
    }
}
