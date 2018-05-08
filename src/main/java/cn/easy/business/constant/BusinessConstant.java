package cn.easy.business.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by superlee on 2017/7/28.
 */
@PropertySource(value = "classpath:business/read.properties",encoding = "UTF-8")
@Component
public final class BusinessConstant {

    @Value("${token_valid_time}")
    public int TOKEN_VALID_TIME; // token有效期
    @Value("${account_lock_time}")
    public int ACCOUNT_LOCK_MINUTE; // 账户锁定时长
    @Value("${password_error_allow}")
    public int PASSWORD_ERROR_ALLOW; // 允许密码错误次数
    @Value("${ip_cal_count}")
    public int IP_CAL_COUNT; // ip访问次数计算点
    @Value("${ip_cal_time}")
    public int IP_CAL_TIME; // ip访问次数计算时间间隔



    @Value("${sms_code_resend}")
    public int SMS_CODE_RESEND; // 重发时间
    @Value("${sms_code_valid}")
    public int SMS_CODE_VALID; // 有效时间
    @Value("${debug}")
    public boolean debug; // 是否debug模式
    @Value("${phone_code_resend}")
    public int PHONE_CODE_RESEND;

}
