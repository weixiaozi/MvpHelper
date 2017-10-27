package mvphelper.qiang.com.mvphelper.domin;

import java.io.Serializable;

/**
 * Created by dell on 2017/10/18.
 */

public class ErrorBean {
    public static String TYPE_DEAL = "1";
    public static String TYPE_SHOW = "2";

    public ErrorBean(String code, String desc, String type) {
        this.code = code;
        this.desc = desc;
        this.type = type;
    }

    public ErrorBean() {

    }

    protected String code;
    protected String desc;
    protected String type = TYPE_DEAL;
}
