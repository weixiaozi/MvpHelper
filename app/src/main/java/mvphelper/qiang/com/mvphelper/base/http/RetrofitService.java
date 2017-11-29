package mvphelper.qiang.com.mvphelper.base.http;

import java.util.Map;

import io.reactivex.Flowable;
import mvphelper.qiang.com.mvphelper.domin.BaseBean;
import mvphelper.qiang.com.mvphelper.domin.CollectionBean;
import mvphelper.qiang.com.mvphelper.domin.ModifyHeadBean;
import mvphelper.qiang.com.mvphelper.domin.TestBean;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by dell on 2017/10/18.
 */

public interface RetrofitService {

    @POST(TestBean.URL)
    @FormUrlEncoded
    Flowable<BaseBean<TestBean>> getClassifyInfo(@FieldMap Map<String, String> params);

    @POST(CollectionBean.URL)
    @FormUrlEncoded
    Flowable<BaseBean<CollectionBean>> getCollectionInfo(@FieldMap Map<String, String> params);

    @POST(ModifyHeadBean.URL)
    @Multipart
    Flowable<BaseBean<ModifyHeadBean>> uploadHeadPic(@PartMap Map<String, RequestBody> params);
}
