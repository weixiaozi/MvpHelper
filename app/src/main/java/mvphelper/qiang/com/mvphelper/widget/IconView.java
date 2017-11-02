package mvphelper.qiang.com.mvphelper.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import mvphelper.qiang.com.mvphelper.base.App;

/**
 * Created by dell on 2017/11/2.
 */

public class IconView extends TextView {
    public IconView(Context context) {
        super(context);
        init();
    }

    public IconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setTypeface(App.mApp.getFromAsset());
    }
}
