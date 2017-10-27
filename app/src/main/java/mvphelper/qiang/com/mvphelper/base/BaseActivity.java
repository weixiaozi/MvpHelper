package mvphelper.qiang.com.mvphelper.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import mvphelper.qiang.com.mvphelper.utils.InputLeakUtil;


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
