package mvphelper.qiang.com.mvphelper.base.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2017/11/29.
 */

public class HeadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("mac", "d2123313123").build();
        chain.proceed(request);

        return null;
    }
}
