package mvphelper.qiang.com.mvphelper.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.utils.InputLeakUtil;
import mvphelper.qiang.com.mvphelper.utils.ScreenUtils;


/**
 * Created by dell on 2017/10/17.
 */

public abstract class BaseActivity<D extends ViewDataBinding, T extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    protected final String TAG = this.getClass().getSimpleName();

    protected T mPresenter;

    protected D mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            if (layoutResID != 0) {//如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
                mBinding = DataBindingUtil.setContentView(this, layoutResID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        immerseUI(getResources().getColor(R.color.red));
        mPresenter = creatPresenter();
        if (isBindEventBus())
            EventBus.getDefault().register(this);
        initData(savedInstanceState);
        initEvent();
    }

    protected abstract T creatPresenter();

    protected abstract void initEvent();

    protected boolean isBindEventBus() {
        return false;
    }


    protected abstract int initView(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    public Activity provideActivity() {
        return this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Integer i) {

    }

    public void immerseUI(int color) {
        ViewGroup rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setPadding(0, ScreenUtils.getStatusBarHeight(this), 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            getWindow().setStatusBarColor(color);
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ScreenUtils.getStatusBarHeight(this));
            statusBarView.setBackgroundColor(color);
            decorView.addView(statusBarView, lp);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destory();
        this.mPresenter = null;
        if (isBindEventBus())
            EventBus.getDefault().unregister(this);
        if (mBinding != null)
            mBinding.unbind();
        InputLeakUtil.fixInputMethodManager(this);
    }
}
