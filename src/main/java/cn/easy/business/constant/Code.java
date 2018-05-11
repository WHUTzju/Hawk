package cn.easy.business.constant;

/**
 * Created by superlee on 2017/11/6.
 */
public enum Code {
    //通用部分
    //通用部分
    UNDEFINED(-1, "未定义"),
    SUCCESS(0, "成功"),
    DEBUG(1, "调试模式"),

    ///账户管理及验证部分

    USER_NOT_EXIST(1200, "用户不存在"),
    USER_NOT_LOGIN(1201, "账户未登陆"),
    USER_FROZEN(1202, "账号已冻结"),

    SMS_CODE_EXPIRE_TIME(1700, "手机验证码过期"),
    SMS_CODE_VERIFY_ERROR(1701, "手机验证码错误"),
    SMS_CODE_RESEND_FREQUENT(1702, "手机验证码过于频繁"),
    SMS_CODE_TPL_NOT_EXIST(1703, "短信验证码模板不存在"),


    GRAPH_CODE_CREATE_ERROR(1710, "图片验证码生成错误"),
    GRAPH_CODE_VERIFY_ERROR(1711, "验证码错误"),
    PHONE_CODE_CREATE_FREQUENT(1712, "获取手机验证码太频繁"),
    PHONE_CODE_VERIFY_ERROR(1713, "手机验证码错误"),
    PHONE_CODE_EXPIRE_TIME(1714, "验证码过期，请重新获取"),
    PHONE_CODE_SEND_ERROR(1715, "手机验证码发送异常"),

    PASSWORD_NOT_EQUAL(1716, "两次密码输入不一致"),
    MAIL_SEND_ERROR(1717, "邮件发送失败"),
    MAIL_CODE_VERIFY_ERROR(1718, "邮箱验证码错误"),
    MAIL_CODE_EXPIRE_TIME(1719, "验证码过期，请重新获取"),
    MAIL_CODE_SEND_ERROR(1720, "邮箱验证码发送异常"),


    ///通用部分
    PARAMETER_ERROR(8001, "参数校验异常"),
    PHONE_FORMAT_ERROR(8002, "邮箱或手机号码格式不对"),

    PERMISSION_DENIED(9001, "权限拒绝"),
    UNKNOWN_ABNORMAL(9002, "未知异常"),
    INVALID_USER(9003, "账户不存在，该用户可能未注册或已失效"),
    ACCOUNT_KEY_EMPTY(9004, "用户名为空"),
    ACCOUNT_ALREADY_EXIST(9005, "用户名已存在"),
    ERROR_PASSWORD(9006, "密码错误"),
    PASSWORD_ERROR_TIME_OVER(9007, "错误次数超过限制"),
    ACCOUNT_STILL_LOCK(9008, "账户仍处于锁定状态"),

    TOKEN_FORMAT_ERROR(9009, "token格式错误，不是有效token"),
    TOKEN_INVALID(9010, "token过期"),
    TOKEN_CRYPT_ERROR(9011, "token加解密异常"),

    JSON_TRANSFER_ERROR(9200, "JSON转化异常"),

    CLIENT_NOT_REGISTER(9300, "业务系统未注册"),
    CLIENT_ALREADY_REGISTER(9301, "业务系统已注册"),

    CLIENT_ROLE_NOT_EXIST(9400, "该业务系统该角色不存在"),

    SYSTEM_ERROR(9999, "系统异常，请稍后重试");

    private int code;
    private String msg;

    // 构造方法
    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static String getMsgByCodeInt(int codeInt) {
        for (Code e : Code.values()) {
            if (e.getCode() == codeInt) {
                return e.msg;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeInt);
    }

    public static Code getCodeByCodeInt(int codeInt) {
        for (Code code : Code.values()) {
            if (code.getCode() == codeInt) {
                return code;
            }
        }
        throw new IllegalArgumentException("未定义的code码:" + codeInt);
    }


}
