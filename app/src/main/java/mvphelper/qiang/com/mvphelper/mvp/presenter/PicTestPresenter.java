package mvphelper.qiang.com.mvphelper.mvp.presenter;

import java.io.File;

import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.model.PicTestModel;

/**
 * Created by dell on 2017/11/29.
 */

public class PicTestPresenter extends NetPresenter {
    private PicTestModel mModel;

    public PicTestPresenter(NetContract.INetView netView) {
        super(netView);
        netModel = mModel = new PicTestModel(this, netView.provideActivity());
    }

    public void uploadPic(File newFile, int uploadpicTag) {
        mModel.uploadPic(newFile,uploadpicTag);
    }
}
