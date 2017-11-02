package mvphelper.qiang.com.mvphelper.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;


/**
 * Created by dell on 2017/10/17.
 */

public class App extends Application {
    public static App mApp;
    private final boolean isDebug = true;
    private Typeface fromAsset;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApp = this;
        if (isDebug) {
            LeakCanary.install(this);
            Logger.addLogAdapter(new AndroidLogAdapter());
        } else {
            //// TODO: 2017/10/17 崩溃日志log

        }
        fromAsset = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public Typeface getFromAsset() {
        return fromAsset;
    }
}
