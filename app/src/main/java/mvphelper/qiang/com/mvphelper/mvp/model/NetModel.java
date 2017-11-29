package mvphelper.qiang.com.mvphelper.mvp.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.Reply;
import mvphelper.qiang.com.mvphelper.base.IBaseModel;
import mvphelper.qiang.com.mvphelper.base.ProgressListener;
import mvphelper.qiang.com.mvphelper.base.http.CacheProvide;
import mvphelper.qiang.com.mvphelper.base.http.RetrofitHelper;
import mvphelper.qiang.com.mvphelper.base.http.RetrofitService;
import mvphelper.qiang.com.mvphelper.base.http.UploadFileRequestBody;
import mvphelper.qiang.com.mvphelper.domin.BaseBean;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.utils.ACache;
import mvphelper.qiang.com.mvphelper.utils.ArmsUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static mvphelper.qiang.com.mvphelper.base.http.ErrorCode.ERROR_CODE_NETWORK;
import static mvphelper.qiang.com.mvphelper.base.http.ErrorCode.ERROR_DESC_NETWORK;
import static mvphelper.qiang.com.mvphelper.domin.ErrorBean.TYPE_SHOW;

/**
 * Created by dell on 2017/10/18.
 */

public class NetModel implements IBaseModel {
    private ACache aCache;
    protected Context mContext;

    private Map<Integer, Disposable> mSubscriptionMap = new ArrayMap<>();
    public NetContract.OnDataLoadingListener dataLoadingListener;
    protected RetrofitService retrofitService;
    protected CacheProvide cacheProvide;
    protected Gson gson;

    public NetModel(NetContract.OnDataLoadingListener dataLoadingListener, Context activity) {
        mContext = activity;
        this.dataLoadingListener = dataLoadingListener;
        retrofitService = RetrofitHelper.getInstance().getRetrofitService();
        cacheProvide = RetrofitHelper.getInstance().getCacheProvide();
        gson = RetrofitHelper.getInstance().getGson();
        if (mContext != null)
            aCache = ACache.get(mContext);
    }

    @Override
    public void cancelNormal(int tag) {
        Disposable disposable = mSubscriptionMap.get(tag);
        if (!disposable.isDisposed())
            disposable.dispose();
    }

    @Override
    public void cancelAll() {
        for (Map.Entry<Integer, Disposable> entry : mSubscriptionMap.entrySet()) {
            if (entry.getValue().isDisposed())
                entry.getValue().dispose();
        }
        mSubscriptionMap.clear();
    }

    /**
     * 设置缓存
     *
     * @param classifyInfo
     * @param tag
     */
    @Override
    public void packageData(Flowable<? extends BaseBean> classifyInfo, int tag, String cacheKey, int cacheTime) {

        if (cacheTime <= 0) {
            dataLoadingListener.startLoading(tag);
            Disposable disposable = classifyInfo.map(new Function<BaseBean, ErrorBean>() {
                @Override
                public ErrorBean apply(BaseBean baseBean) throws Exception {
                    aCache.put(ArmsUtils.encodeToMD5(cacheKey), gson.toJson(baseBean.getContent()));
                    return baseBean.getContent();
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(testBeanBaseBean -> {
                        dataLoadingListener.onSuccess(testBeanBaseBean, tag, true);
                    }, Throwable -> {
                        String objString = aCache.getAsString(ArmsUtils.encodeToMD5(cacheKey));
                        if (!TextUtils.isEmpty(objString)) {
                            dataLoadingListener.onSuccess(gson.fromJson(objString, ErrorBean.class), tag, false);
                        }
                        dataLoadingListener.onError(new ErrorBean(ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, TYPE_SHOW), tag);
                        Logger.log(Logger.ERROR, ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, Throwable);
                    });
            mSubscriptionMap.put(tag, disposable);

        } else {
            String objString = aCache.getAsString(ArmsUtils.encodeToMD5(cacheKey));
            if (TextUtils.isEmpty(objString)) {
                dataLoadingListener.startLoading(tag);
                Disposable disposable = classifyInfo.map(new Function<BaseBean, ErrorBean>() {
                    @Override
                    public ErrorBean apply(BaseBean baseBean) throws Exception {
                        aCache.put(ArmsUtils.encodeToMD5(cacheKey), gson.toJson(baseBean.getContent()), cacheTime);
                        return baseBean.getContent();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(testBeanBaseBean -> {
                            dataLoadingListener.onSuccess(testBeanBaseBean, tag, true);
                        }, Throwable -> {
                            dataLoadingListener.onError(new ErrorBean(ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, TYPE_SHOW), tag);
                            Logger.log(Logger.ERROR, ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, Throwable);
                        });
                mSubscriptionMap.put(tag, disposable);
            } else {
                dataLoadingListener.onSuccess(gson.fromJson(objString, ErrorBean.class), tag, false);
            }
        }

    }

    /**
     * 不设置缓存
     *
     * @param classifyInfo
     * @param tag
     */
    @Override
    public void packageData(Flowable<? extends BaseBean> classifyInfo, int tag) {
        dataLoadingListener.startLoading(tag);
        Disposable disposable = classifyInfo.map(new Function<BaseBean, ErrorBean>() {
            @Override
            public ErrorBean apply(BaseBean baseBean) throws Exception {
                return baseBean.getContent();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(testBeanBaseBean -> {
                    dataLoadingListener.onSuccess(testBeanBaseBean, tag, true);
                }, Throwable -> {
                    dataLoadingListener.onError(new ErrorBean(ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, TYPE_SHOW), tag);
                    Logger.log(Logger.ERROR, ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, Throwable);
                });
        mSubscriptionMap.put(tag, disposable);

    }

    public void packageDataWithCache(Flowable<? extends Reply<? extends BaseBean>> classifyInfo, int tag) {
        dataLoadingListener.startLoading(tag);
        Disposable disposable = classifyInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Reply<? extends BaseBean>>() {
                    @Override
                    public void accept(Reply<? extends BaseBean> reply) {
                        BaseBean data = reply.getData();
                        dataLoadingListener.onSuccess(data.getContent(), tag, true);
                    }
                }, throwable -> {
                    dataLoadingListener.onError(new ErrorBean(ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, TYPE_SHOW), tag);
                    Log.e("bbbbbbb", throwable.getMessage());
                });


       /* Disposable disposable = classifyInfo.map(new Function<Reply<? extends BaseBean>, ErrorBean>() {
            @Override
            public ErrorBean apply(@NonNull Reply<? extends BaseBean> reply) throws Exception {
                return reply.getData().getContent();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(testBeanBaseBean -> {
                    dataLoadingListener.onSuccess(testBeanBaseBean, tag, true);
                }, throwable -> {
                    dataLoadingListener.onError(new ErrorBean(ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, TYPE_SHOW), tag);
                    Log.e("bbbbbbb", throwable.getMessage());
                    Logger.log(Logger.ERROR, ERROR_CODE_NETWORK, ERROR_DESC_NETWORK, throwable);
                });*/
        mSubscriptionMap.put(tag, disposable);
    }

    /**
     * 上传文件时包装request
     * @param params
     * @param file
     * @param tag
     * @return
     */
    public Map<String, RequestBody> packParamsToRequestBody(Map<String, String> params, File file, int tag) {
        Map<String, RequestBody> requestBodyMap = new ArrayMap<>();
        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, MediaType.parse(RetrofitHelper.getInstance().getMimeType(file.getAbsolutePath())), new ProgressListener() {
            @Override
            public void onProgress(long currentBytes, long contentLength, boolean done) {
                dataLoadingListener.onProgress((int) (100f * currentBytes / contentLength), tag, done);
            }
        });
        requestBodyMap.put("file\"; filename=\"" + file.getName(), fileRequestBody);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            requestBodyMap.put(entry.getKey(), RequestBody.create(null, entry.getValue()));
        }
        return requestBodyMap;
    }
}
