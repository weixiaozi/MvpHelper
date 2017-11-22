package mvphelper.qiang.com.mvphelper.ui.service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

import mvphelper.qiang.com.mvphelper.base.Constant;
import mvphelper.qiang.com.mvphelper.domin.DownLoadBean;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.presenter.NetPresenter;
import mvphelper.qiang.com.mvphelper.mvp.presenter.UpdataApkPresenter;
import mvphelper.qiang.com.mvphelper.utils.LogUtil;

import static mvphelper.qiang.com.mvphelper.base.Constant.AUTHORITY;

/**
 * Created by dell on 2017/11/22.
 */

public class UpdateApkService extends IntentService implements NetContract.INetView {
    public static final int DOWNAPK_TAG = 1;

    public UpdateApkService() {
        super("updateApkService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String downUrl = intent.getStringExtra("downurl");
        UpdataApkPresenter updataApkPresenter = new UpdataApkPresenter(this);
        updataApkPresenter.downloadApk(downUrl, Constant.STORAGE_PATH, "mvpdemo.apk", DOWNAPK_TAG);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(ErrorBean errorBean, int tag) {
        LogUtil.i("aaaaa"+errorBean.desc);
    }

    @Override
    public Activity provideActivity() {
        return null;
    }

    @Override
    public void setData(ErrorBean errorBean, int tag) {
        switch (tag) {
            case DOWNAPK_TAG:
                if (errorBean instanceof DownLoadBean) {
                    DownLoadBean downLoadBean = (DownLoadBean) errorBean;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 24) {
                        Uri uriForFile = FileProvider.getUriForFile(getApplicationContext(), AUTHORITY, new File(downLoadBean.getPath()));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(new File(downLoadBean.getPath())), "application/vnd.android.package-archive");
                    }
                    getApplication().startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void progress(int precent, int tag) {
        LogUtil.i("kkkkkkkkk", precent + "___" + tag);
    }
}
