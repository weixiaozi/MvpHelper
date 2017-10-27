package mvphelper.qiang.com.mvphelper.utils;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dell on 2017/10/19.
 */

public class RxUtil {
    /**
     * 简化RX线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> fixScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }
}
