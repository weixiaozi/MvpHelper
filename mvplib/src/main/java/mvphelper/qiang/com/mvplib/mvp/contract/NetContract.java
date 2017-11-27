package mvphelper.qiang.com.mvplib.mvp.contract;

import mvphelper.qiang.com.mvplib.base.IBasePresenter;
import mvphelper.qiang.com.mvplib.base.IBaseView;
import mvphelper.qiang.com.mvplib.domin.ErrorBean;

/**
 * Created by dell on 2017/10/18.
 */

public interface NetContract {
    //网络请求
    interface INetView extends IBaseView {
        void setData(ErrorBean errorBean, int tag);


        void progress(int precent, int tag);
    }

    //包含刷新加载
    interface IRefreshAndLoadMoreView {

        void noData();

        void noMoreData();

    }

    interface INetPresenter extends IBasePresenter {
    }

    interface OnDataLoadingListener {
        void onSuccess(ErrorBean o, int tag, boolean isNetWork);

        void onError(ErrorBean errorBean, int tag);

        void onProgress(int percent, int tag, boolean isDone);
    }
}
