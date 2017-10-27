package mvphelper.qiang.com.mvphelper.ui.activity;

import android.Manifest;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.base.BaseActivity;
import mvphelper.qiang.com.mvphelper.databinding.ActivityTestBinding;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.presenter.TestPresenter;

public class TestActivity extends BaseActivity<ActivityTestBinding, TestPresenter> implements NetContract.INetView {

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_test;
    }

    @Override
    protected TestPresenter creatPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
            if (aBoolean)
                mPresenter.getBphp(1);
        });

    }


    @Override
    protected void initEvent() {
        mBinding.buttontest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TestActivity.this, "hhhhhh", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(ErrorBean errorBean, int tag) {
        Log.i("kkkkkkk", errorBean.toString() + tag);
    }

    @Override
    public void showError(ErrorBean errorBean, int tag) {

    }

    @Override
    public void progress(int precent, int tag) {

    }


}
