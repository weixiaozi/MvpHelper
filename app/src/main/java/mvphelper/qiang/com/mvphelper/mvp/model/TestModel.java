package mvphelper.qiang.com.mvphelper.mvp.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.ArrayMap;
import android.util.Log;
import android.util.LruCache;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;
import mvphelper.qiang.com.mvphelper.domin.BaseBean;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.domin.TestBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import okhttp3.internal.cache.DiskLruCache;

/**
 * Created by dell on 2017/10/20.
 */

public class TestModel extends NetModel {
    public TestModel(NetContract.OnDataLoadingListener dataLoadingListener, Activity activity) {
        super(dataLoadingListener, activity);
    }

    public void getBphp(int tag) {
        ArrayMap<String, String> params = new ArrayMap<>();
        Flowable<BaseBean<TestBean>> classifyInfo = retrofitService.getClassifyInfo(params);

        packageData(classifyInfo, tag);
//        Flowable<Reply<BaseBean<TestBean>>> classifyInfo1 = cacheProvide.getClassifyInfo(classifyInfo, new EvictProvider(false));
//        packageDataWithCache(classifyInfo1, tag);

    }

}
