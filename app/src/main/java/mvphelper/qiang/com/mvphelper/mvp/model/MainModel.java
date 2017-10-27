package mvphelper.qiang.com.mvphelper.mvp.model;

import android.content.Context;

import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;

/**
 * Created by dell on 2017/10/24.
 */

public class MainModel extends NetModel {
    public MainModel(NetContract.OnDataLoadingListener dataLoadingListener, Context activity) {
        super(dataLoadingListener, activity);
    }
}
