package mvphelper.qiang.com.mvplib.base.http;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import mvphelper.qiang.com.mvplib.base.App;
import mvphelper.qiang.com.mvplib.utils.DataHelper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static mvphelper.qiang.com.mvplib.base.Constant.BASEURL;

/**
 * Created by dell on 2017/10/20.
 */

public class RetrofitHelper {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private CacheProvide cacheProvide;
    private Gson gson;

    public static RetrofitHelper getInstance() {
        return InstanceHolder.instance;
    }

    static class InstanceHolder {
        private static RetrofitHelper instance = new RetrofitHelper();
    }

    public RetrofitHelper() {
        initOkhttp();
        initRetrofit();
        initCache();
        initGson();
    }

    private void initGson() {
        gson = new Gson();
    }

    private CacheProvide initCache() {
        File cacheDirectory = new File(DataHelper.getCacheFile(App.mApp), "RxCache");
        return cacheProvide = new RxCache.Builder().setMaxMBPersistenceCache(100).persistence(DataHelper.makeDirs(cacheDirectory), new GsonSpeaker()).using(CacheProvide.class);
    }

    private RetrofitService initRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl(BASEURL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        return retrofitService = retrofit.create(RetrofitService.class);
    }

    private void initOkhttp() {
        okHttpClient = new OkHttpClient.Builder().readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();
    }

    public RetrofitService getRetrofitService() {
        if (retrofitService == null)
            initRetrofit();
        return retrofitService;
    }

    public CacheProvide getCacheProvide() {
        if (cacheProvide == null)
            initCache();
        return cacheProvide;
    }

    public Gson getGson() {
        if (gson == null)
            initGson();
        return gson;
    }
}
