package mvphelper.qiang.com.mvplib.domin;

import java.io.Serializable;

/**
 * Created by dell on 2017/10/19.
 */

public class BaseBean<T extends ErrorBean> implements Serializable {
    private String status;
    private T content;

    public T getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status='" + status + '\'' +
                ", content=" + content +
                '}';
    }
}
