package mvphelper.qiang.com.mvphelper.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.base.BaseFragment;
import mvphelper.qiang.com.mvphelper.databinding.FragmentTest1Binding;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.presenter.TestPresenter;
import mvphelper.qiang.com.mvphelper.ui.adapter.TestAdapter;

/**
 * Created by dell on 2017/10/24.
 */

public class Test1Fragment extends BaseFragment<FragmentTest1Binding, TestPresenter> implements NetContract.INetView {

    private String url = "http://a.hiphotos.baidu.com/image/pic/item/6609c93d70cf3bc7176dd658db00baa1cd112a10.jpg";
    private String url1 = "http://f.hiphotos.baidu.com/image/pic/item/caef76094b36acaf0b35e44876d98d1000e99ca8.jpg";
    private String url2 = "http://d.hiphotos.baidu.com/image/pic/item/a8014c086e061d95a17f698c71f40ad162d9ca4d.jpg";
    private String url3 = "http://e.hiphotos.baidu.com/image/pic/item/c8177f3e6709c93d24085b43953df8dcd000548f.jpg";
    private String url4 = "http://g.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c1e6b1bc7f1f2b21193138a13.jpg";
    private String url5 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509017612394&di=ea48ac8dad2e5096b76c866a0e970073&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fc2fdfc039245d688bb61de94a2c27d1ed21b249a.jpg";
    private String url6 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509017612392&di=68297110c72e28b90af28d3f35cf8266&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1210%2F08%2Fc1%2F14307187_1349676294934.jpg";

    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(ErrorBean errorBean, int tag) {

    }

    @Override
    public void setData(ErrorBean errorBean, int tag) {

    }

    @Override
    public void progress(int precent, int tag) {

    }

    @Override
    protected TestPresenter creatPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.fragment_test1;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        mBinding.tvTestfragment.setText(arguments.getString("testdata"));
        List datas = new ArrayList();
        Flowable.range(1, 10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                datas.add(url);
                datas.add(url1);
                datas.add(url2);
                datas.add(url3);
                datas.add(url4);
                datas.add(url5);
                datas.add(url6);
            }
        });
        mBinding.rcycleTest.setAdapter(new TestAdapter(provideActivity(), datas));
        mBinding.rcycleTest.setLayoutManager(new GridLayoutManager(provideActivity(), 3, LinearLayoutManager.VERTICAL, false));

    }
}
