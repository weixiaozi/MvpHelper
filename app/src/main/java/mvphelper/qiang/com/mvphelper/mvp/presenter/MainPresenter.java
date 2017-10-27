package mvphelper.qiang.com.mvphelper.mvp.presenter;

import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.model.MainModel;

/**
 * Created by dell on 2017/10/24.
 */

public class MainPresenter extends NetPresenter {

    public MainPresenter(NetContract.INetView netView) {
        super(netView);
        netModel = new MainModel(this, netView.provideActivity());
    }
}
