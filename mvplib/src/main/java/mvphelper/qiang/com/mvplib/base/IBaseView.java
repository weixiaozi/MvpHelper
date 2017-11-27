package mvphelper.qiang.com.mvplib.base;

import android.app.Activity;

import mvphelper.qiang.com.mvplib.domin.ErrorBean;

/**
 * Created by dell on 2017/10/17.
 */

public interface IBaseView {

    void showLoading();

    void hideLoading();

    void showError(ErrorBean errorBean, int tag);

    /**
     * 返回Activity
     *
     * @return
     */
    Activity provideActivity();
}
