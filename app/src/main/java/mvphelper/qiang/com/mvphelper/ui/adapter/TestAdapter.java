package mvphelper.qiang.com.mvphelper.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.base.App;
import mvphelper.qiang.com.mvphelper.base.BaseRvAdapter;
import mvphelper.qiang.com.mvphelper.databinding.ItemTest1Binding;
import mvphelper.qiang.com.mvphelper.utils.ImageLoader;

/**
 * Created by dell on 2017/10/27.
 */

public class TestAdapter extends BaseRvAdapter<ItemTest1Binding> {
    private List<String> datas;

    public TestAdapter(Context mContext, List datas) {
        super(mContext);
        this.datas = datas;
    }

    @Override
    protected MyViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return bind(R.layout.item_test1);
    }

    @Override
    protected void onBindHolder(MyViewHolder holder, int position) {
        new ImageLoader().setUrl(datas.get(position)).into(holder.mBinding.itemIv).build();
        holder.mBinding.itemIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(App.mApp, holder.getLayoutPosition() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
