package mvphelper.qiang.com.mvphelper.mvp.model;

import android.content.Context;
import android.util.ArrayMap;

import java.io.File;
import java.util.Map;

import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by dell on 2017/11/29.
 */

public class PicTestModel extends NetModel {
    public PicTestModel(NetContract.OnDataLoadingListener dataLoadingListener, Context activity) {
        super(dataLoadingListener, activity);
    }

    public void uploadPic(File newFile, int uploadpicTag) {
        Map<String, String> params = new ArrayMap<>();
        params.put("uid", "1895");
        params.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7");
        packageData(retrofitService.uploadHeadPic(packParamsToRequestBody(params, newFile,uploadpicTag)), uploadpicTag);
    }

}
