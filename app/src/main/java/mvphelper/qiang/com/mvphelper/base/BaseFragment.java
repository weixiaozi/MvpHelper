package mvphelper.qiang.com.mvphelper.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import mvphelper.qiang.com.mvphelper.utils.InputLeakUtil;

/**
 * Created by dell on 2017/10/24.
 */

public abstract class BaseFragment<T extends ViewDataBinding, P extends IBasePresenter> extends Fragment implements IBaseView {
    protected final String TAG = this.getClass().getSimpleName();

    protected P mPresenter;
    protected T mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBindEventBus())
            EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int layoutId = initView(savedInstanceState);
        if (layoutId != 0) {
            //绑定到butterknife
            View view = inflater.inflate(layoutId, container, false);
            mBinding = DataBindingUtil.bind(view);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
        initEvent();
    }

    protected abstract P creatPresenter();

    protected abstract void initEvent();

    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public Activity provideActivity() {
        return getActivity();
    }

    protected abstract int initView(Bundle savedInstanceState);

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destory();
        this.mPresenter = null;
        if (isBindEventBus())
            EventBus.getDefault().unregister(this);
        if (mBinding != null)
            mBinding.unbind();
        InputLeakUtil.fixInputMethodManager(getActivity());
    }
}
