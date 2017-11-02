package mvphelper.qiang.com.mvphelper.mvp.contract;

import java.util.Map;

import mvphelper.qiang.com.mvphelper.base.IBasePresenter;
import mvphelper.qiang.com.mvphelper.base.IBaseView;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;

/**
 * Created by dell on 2017/10/18.
 */

public interface NetContract {
    interface INetView extends IBaseView {
        void setData(ErrorBean errorBean, int tag);


        void progress(int precent, int tag);
    }

    interface INetPresenter extends IBasePresenter {
    }

    interface OnDataLoadingListener {
        void onSuccess(ErrorBean o, int tag, boolean isNetWork);

        void onError(ErrorBean errorBean, int tag);

        void onProgress(int percent, int tag, boolean isDone);
    }
}