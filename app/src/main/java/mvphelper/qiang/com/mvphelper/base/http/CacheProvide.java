package mvphelper.qiang.com.mvphelper.base.http;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Expirable;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Migration;
import io.rx_cache2.Reply;
import io.rx_cache2.SchemeMigration;
import mvphelper.qiang.com.mvphelper.domin.BaseBean;
import mvphelper.qiang.com.mvphelper.domin.TestBean;

/**
 * Created by dell on 2017/10/20.
 */
public interface CacheProvide {
    @Expirable(false)
    @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
    Flowable<Reply<BaseBean<TestBean>>> getClassifyInfo(Flowable<BaseBean<TestBean>> test, EvictProvider evictProvider);
}
