package cn.easy.business.constant;

/**
 * Created by superlee on 2017/11/9.
 */
public enum AccountStatus {
    VALID(0, "有效"),
    INVALID(1, "无效"),
    FROZEN(2, "冻结"),
    LOCK(3, "锁定");

    private int code;
    private String status;

    AccountStatus(int code, String status) {
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
