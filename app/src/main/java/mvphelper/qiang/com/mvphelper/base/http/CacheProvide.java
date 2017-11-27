package mvphelper.qiang.com.mvphelper.base.http;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Expirable;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Migration;
import io.rx_cache2.Reply;
import io.rx_cache2.SchemeMigration;
import mvphelper.qiang.com.mvphelper.domin.BaseBean;
import mvphelper.qiang.com.mvphelper.domin.CollectionBean;
import mvphelper.qiang.com.mvphelper.domin.TestBean;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell on 2017/10/20.
 */
public interface CacheProvide {
    @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
    Flowable<Reply<BaseBean<TestBean>>> getClassifyInfo(Flowable<BaseBean<TestBean>> test, EvictProvider evictProvider);

    @LifeCache(duration = 100, timeUnit = TimeUnit.MINUTES)
    Flowable<Reply<BaseBean<CollectionBean>>> getCollectionInfo(Flowable<BaseBean<CollectionBean>> test, DynamicKey idLastUserQueried, EvictProvider evictProvider);
}
