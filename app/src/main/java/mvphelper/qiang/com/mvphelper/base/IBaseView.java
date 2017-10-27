package mvphelper.qiang.com.mvphelper.base;

import android.app.Activity;

import mvphelper.qiang.com.mvphelper.domin.ErrorBean;

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
