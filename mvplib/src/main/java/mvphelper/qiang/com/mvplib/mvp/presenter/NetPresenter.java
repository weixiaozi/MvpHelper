package mvphelper.qiang.com.mvplib.mvp.presenter;


import mvphelper.qiang.com.mvplib.base.IBaseModel;
import mvphelper.qiang.com.mvplib.domin.ErrorBean;
import mvphelper.qiang.com.mvplib.mvp.contract.NetContract;

/**
 * Created by dell on 2017/10/17.
 */
public class NetPresenter implements NetContract.INetPresenter, NetContract.OnDataLoadingListener {
    private NetContract.INetView netView;

    public NetPresenter(NetContract.INetView netView) {
        this.netView = netView;
    }

    public IBaseModel netModel;

    @Override
    public void destory() {
        if (netModel != null)
            netModel.cancelAll();
    }


    @Override
    public void onSuccess(ErrorBean o, int tag, boolean isNetWork) {
        netView.setData(o, tag);
        netView.hideLoading();
    }

    @Override
    public void onError(ErrorBean errorBean, int tag) {
        netView.showError(errorBean, tag);
        netView.hideLoading();
    }

    @Override
    public void onProgress(int percent, int tag, boolean isDone) {
        netView.progress(percent, tag);
    }
}
