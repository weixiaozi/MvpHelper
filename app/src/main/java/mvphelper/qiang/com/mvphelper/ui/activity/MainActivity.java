package mvphelper.qiang.com.mvphelper.ui.activity;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

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
import mvphelper.qiang.com.mvphelper.ui.service.UpdateApkService;
import mvphelper.qiang.com.mvphelper.utils.LogUtil;
import mvphelper.qiang.com.mvphelper.utils.SystemUtil;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        /*new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(provideActivity(), UpdateApkService.class);
                intent.putExtra("downurl", "http://pre.huanpeng.com/api/app/download.php?channel=8001");
                startService(intent);
            }
        });*/

        setSupportActionBar(mBinding.mainToolbar);
        mBinding.mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "back", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        startActivity(new Intent(provideActivity(), Test2Activity.class));
                        break;
                    case R.id.action_search1:
                        startActivity(new Intent(provideActivity(), TestCoordinatorActivity.class));
                        break;
                    case R.id.action_search2:
                        int model = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                        switch (model) {
                            case Configuration.UI_MODE_NIGHT_NO:
                                getDelegate().setLocalNightMode(
                                        AppCompatDelegate.MODE_NIGHT_YES);
                                break;
                            case Configuration.UI_MODE_NIGHT_YES:
                                getDelegate().setLocalNightMode(
                                        AppCompatDelegate.MODE_NIGHT_NO);
                                break;
                            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                                getDelegate().setLocalNightMode(
                                        AppCompatDelegate.MODE_NIGHT_AUTO);
                                break;
                        }
                        recreate();
                        break;
                    case R.id.action_search3:
                        startActivity(new Intent(provideActivity(), TestTabLayoutActivity.class));
                        break;
                }
                return true;
            }
        });
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
