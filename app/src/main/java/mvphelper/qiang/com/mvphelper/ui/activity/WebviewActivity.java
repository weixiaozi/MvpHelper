package mvphelper.qiang.com.mvphelper.ui.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.base.BaseActivity;
import mvphelper.qiang.com.mvphelper.databinding.ActivityWebviewBinding;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.presenter.TestPresenter;

import static android.util.Base64.NO_WRAP;
import static mvphelper.qiang.com.mvphelper.base.Constant.GAME_PATH;

public class WebviewActivity extends BaseActivity<ActivityWebviewBinding, TestPresenter> implements NetContract.INetView {

    private WebView mWebView;

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(ErrorBean errorBean, int tag) {

    }

    @Override
    protected TestPresenter creatPresenter() {
        return new TestPresenter(this);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_webview;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initData(Bundle savedInstanceState) {
        mWebView = new WebView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);
        mBinding.parent.addView(mWebView);
//        mBinding.title.txtTopCenter.setText(getString(R.string.my_earnings));

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.addJavascriptInterface(this, "phonePlus");
        mWebView.setWebViewClient(new MyWebViewClient(this));
//        mWebView.loadUrl(MYEARNINGURL);
        mWebView.loadUrl("http://htmltest.proxy.6rooms.cc/test01.html");
//        mWebView.loadUrl("http://htmltest.andriod.6rooms.cc/dezhoupuke/index.html");
       /* OkHttpClient build = new OkHttpClient.Builder().build();
        Request build1 = new Request.Builder().url("http://htmltest.andriod.6rooms.cc/test01.html").build();
        build.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mWebView.loadDataWithBaseURL("file:///",response.body().string(),"text/html;charset=utf-8",null,null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });*/
    }
    static class MyWebViewClient extends WebViewClient {
        private WeakReference<WebviewActivity> mReference;

        public MyWebViewClient(WebviewActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebResourceResponse webResourceResponse = null;
            if (url.contains("getfromapp")) {
                try {
                    String filename = Uri.parse(url).getQueryParameter("filename");
                    InputStream open = mReference.get().getAssets().open("img/"+filename);
                    webResourceResponse = new WebResourceResponse("image/png", "UTF-8", open);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return webResourceResponse;
        }

        @SuppressLint("NewApi")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Uri url = request.getUrl();
            String uri = url.toString();
            WebResourceResponse webResourceResponse = null;
            if (uri.contains("getfromapp")) {
                try {
                    String filename = url.getQueryParameter("filename");
                    File sdFile = new File(GAME_PATH, filename);
                    if (sdFile.exists()){
                        FileInputStream fileInputStream = new FileInputStream(sdFile);
                        webResourceResponse = new WebResourceResponse("image/png", "UTF-8", fileInputStream);
                    }else {
                        InputStream open = mReference.get().getAssets().open("img/"+filename);
                        webResourceResponse = new WebResourceResponse("image/png", "UTF-8", open);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return webResourceResponse;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mReference.get().mWebView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            /*try {
                String ioToBase64 = mReference.get().ioToBase64();
                mReference.get().mWebView.loadUrl("javascript:aaa('data:image/png;base64," + ioToBase64 + "')");
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

    }

    public String ioToBase64() throws IOException {
        String fileName = "number_1.png"; //源文件
        String strBase64 = null;
        try {
            InputStream in = getAssets().open(fileName);
            // in.available()返回文件的字节长度
            byte[] bytes = new byte[in.available()];
            // 将文件中的内容读入到数组中
            in.read(bytes);
            strBase64 = Base64.encodeToString(bytes, NO_WRAP);
            ;      //将字节流数组转换为字符串
            in.close();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return strBase64;
    }

    @Override
    public void setData(ErrorBean errorBean, int tag) {

    }

    @Override
    public void progress(int precent, int tag) {

    }
}
