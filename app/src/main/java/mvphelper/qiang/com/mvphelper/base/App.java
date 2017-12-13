package mvphelper.qiang.com.mvphelper.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.squareup.leakcanary.LeakCanary;

import mvphelper.qiang.com.mvphelper.function.CrashHandler;


/**
 * Created by dell on 2017/10/17.
 */

public class App extends Application {
    public static App mApp;
    private final boolean isDebug = true;
    private Typeface fromAsset;

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static App getInstance() {
        return mApp;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApp = this;
        if (isDebug) {
            LeakCanary.install(this);
            CrashHandler.getInstance().init(this);
        } else {
            CrashHandler.getInstance().init(this);
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
