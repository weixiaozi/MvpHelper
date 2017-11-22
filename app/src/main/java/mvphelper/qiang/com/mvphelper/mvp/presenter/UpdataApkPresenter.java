package mvphelper.qiang.com.mvphelper.mvp.presenter;

import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.model.UpdataApkModel;

/**
 * Created by dell on 2017/11/22.
 */

public class UpdataApkPresenter extends NetPresenter {
    private UpdataApkModel mModel;

    public UpdataApkPresenter(NetContract.INetView netView) {
        super(netView);
        netModel = mModel = new UpdataApkModel(this, netView.provideActivity());
    }

    public void downloadApk(String downUrl, String path, String name, int tag) {
        mModel.downloadApk(downUrl, path, name, tag);
    }
}
