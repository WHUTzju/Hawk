package cn.easy.business.constant;

/**
 * Created by superlee on 2017/11/8.
 */
public enum ClientStatus {
    NORMAL(0, "正常");

    private int code;
    private String status;

    ClientStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
