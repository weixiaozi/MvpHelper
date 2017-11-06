package mvphelper.qiang.com.mvphelper.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.base.BaseActivity;
import mvphelper.qiang.com.mvphelper.databinding.ActivityTestBinding;
import mvphelper.qiang.com.mvphelper.databinding.ActivityTestTabLayoutBinding;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.presenter.TestPresenter;
import mvphelper.qiang.com.mvphelper.ui.fragment.Test1Fragment;
import mvphelper.qiang.com.mvphelper.ui.fragment.Test2Fragment;

public class TestTabLayoutActivity extends BaseActivity<ActivityTestTabLayoutBinding, TestPresenter> implements NetContract.INetView {

    @Override
    protected TestPresenter creatPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_test_tab_layout;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBinding.tabViewpager.setAdapter(new TabAdapter(Arrays.asList("tab1", "tab2", "tab3", "tab4", "tab5", "tab6", "tab7", "tab8", "tab9", "tab10"), Arrays.asList(new Test1Fragment(),
                new Test2Fragment(), new Test1Fragment(), new Test2Fragment(), new Test1Fragment(), new Test2Fragment(), new Test1Fragment(), new Test2Fragment(), new Test1Fragment(), new Test2Fragment())));
        mBinding.tablayout.setupWithViewPager(mBinding.tabViewpager);
    }

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

    class TabAdapter extends FragmentStatePagerAdapter {
        private List<String> tabs;
        private List<Fragment> fragmentList;

        public TabAdapter(List<String> tabs, List<Fragment> fragmentList) {
            super(TestTabLayoutActivity.this.getSupportFragmentManager());
            this.tabs = tabs;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position);
        }
    }
}
