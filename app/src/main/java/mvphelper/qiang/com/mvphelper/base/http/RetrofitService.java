package mvphelper.qiang.com.mvphelper.base.http;

import java.util.Map;

import io.reactivex.Flowable;
import mvphelper.qiang.com.mvphelper.domin.BaseBean;
import mvphelper.qiang.com.mvphelper.domin.TestBean;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by dell on 2017/10/18.
 */

public interface RetrofitService {

    @POST(TestBean.URL)
    @FormUrlEncoded
    Flowable<BaseBean<TestBean>> getClassifyInfo(@FieldMap Map<String, String> params);
}
