package mvphelper.qiang.com.mvphelper.domin;

/**
 * Created by dell on 2016/11/23 17:46.
 */

public class ModifyHeadBean extends ErrorBean {
    public static final String URL = "api/upload/uploadUserPic.php";
    private String head = "";
    private String picCheckStat = "";

    public String getHead() {
        return head;
    }

    public String getPicCheckStat() {
        return picCheckStat;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setPicCheckStat(String picCheckStat) {
        this.picCheckStat = picCheckStat;
    }
}

