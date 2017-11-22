package mvphelper.qiang.com.mvphelper.mvp.presenter;

import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.model.TestModel;

/**
 * Created by dell on 2017/10/17.
 */

public class TestPresenter extends NetPresenter {
    private TestModel mModel;

    public TestPresenter(NetContract.INetView netView) {
        super(netView);
        netModel = mModel = new TestModel(this, netView.provideActivity());
    }


    public void getBphp(int tag) {
        mModel.getBphp(tag);
    }

}
