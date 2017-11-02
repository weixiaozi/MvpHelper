package mvphelper.qiang.com.mvphelper.ui.activity;

import android.app.Fragment;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.base.BaseActivity;
import mvphelper.qiang.com.mvphelper.base.BaseFragment;
import mvphelper.qiang.com.mvphelper.databinding.ActivityMainBinding;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.presenter.MainPresenter;
import mvphelper.qiang.com.mvphelper.ui.adapter.MainFragmentAdapter;
import mvphelper.qiang.com.mvphelper.ui.fragment.Test1Fragment;
import mvphelper.qiang.com.mvphelper.ui.fragment.Test2Fragment;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainPresenter> implements NetContract.INetView {

    @Override
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
    protected MainPresenter creatPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void initEvent() {
        mBinding.rgroupMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbtn_main_1:
                        mBinding.viewpagerMain.setCurrentItem(0, false);
                        break;
                    case R.id.rbtn_main_2:
                        mBinding.viewpagerMain.setCurrentItem(1, false);
                        break;
                    case R.id.rbtn_main_3:
                        mBinding.viewpagerMain.setCurrentItem(2, false);
                        break;
                    case R.id.rbtn_main_4:
                        mBinding.viewpagerMain.setCurrentItem(3, false);
                        break;
                }
            }
        });
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        List<BaseFragment> fragments = new ArrayList<>();
        Test1Fragment test1Fragment = new Test1Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("testdata", "test1");
        test1Fragment.setArguments(bundle);

        Test2Fragment test2Fragment = new Test2Fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("testdata", "test2");
        test2Fragment.setArguments(bundle2);

        Test1Fragment test3Fragment = new Test1Fragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("testdata", "test3");
        test3Fragment.setArguments(bundle3);

        Test1Fragment test4Fragment = new Test1Fragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("testdata", "test4");
        test4Fragment.setArguments(bundle4);
        fragments.add(test1Fragment);
        fragments.add(test2Fragment);
        fragments.add(test3Fragment);
        fragments.add(test4Fragment);
        mBinding.viewpagerMain.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(), fragments));
        mBinding.viewpagerMain.setCurrentItem(0);
        mBinding.viewpagerMain.setOffscreenPageLimit(4);
//        GlideApp.with(this).load("http://a.hiphotos.baidu.com/image/pic/item/6609c93d70cf3bc7176dd658db00baa1cd112a10.jpg").into(iv_mainactivity);

    }
}
