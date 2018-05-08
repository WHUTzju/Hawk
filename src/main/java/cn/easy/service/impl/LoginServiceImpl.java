package cn.easy.service.impl;

import cn.easy.business.constant.BaseConstant;
import cn.easy.business.constant.BusinessConstant;
import cn.easy.business.constant.Code;
import cn.easy.business.exception.TokenException;
import cn.easy.business.response.BaseResult;
import cn.easy.dal.entity.AccountEntity;
import cn.easy.dal.repository.AccountEntityRepo;
import cn.easy.model.SmsCode;
import cn.easy.model.Token;
import cn.easy.service.LoginService;
import cn.easy.service.TokenService;
import cn.easy.util.CommonUtil;
import cn.easy.util.DateUtil;
import cn.easy.util.RandomCodeUtil;
import cn.easy.util.VerifyCodeUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangrui on 2018/5/7.
 */
@Service
public class LoginServiceImpl implements LoginService {
    /**
     * 1）	点击立即注册
     * 2）	输入注册邮箱/手机号
     * 3）	根据输入的手机号或者邮箱，对应发送其6位数验证码，卖家用户回执验证码
     * 4）	输入密码，并再次输入密码，确认密码
     * 5）	输入实时验证码，避免机器人注册
     * 确认后提交信息
     */
    private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);
    //放到session中的key
    private static final String GRAPHCODEKEY = "GRAPHVERIFYCODE";
    //todo
    private static Map<String, SmsCode> smsCodeMap = new HashMap<>();

    @Autowired
    AccountEntityRepo accountEntityRepo;

    @Autowired
    TokenService tokenService;

    @Autowired
    BusinessConstant businessConstant;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public BaseResult registerWithPhone(String phone, String password, int roleCode) throws TokenException {
        BaseResult baseResult = new BaseResult();
        if (!ObjectUtils.isEmpty(accountEntityRepo.findByPhone(phone))) {
            baseResult.returnWithoutValue(Code.ACCOUNT_ALREADY_EXIST);
            return baseResult;
        }


        //
        AccountEntity accountEntity = AccountEntity.builder().
                phone(phone)
                .roleCode(roleCode)
                .password(DigestUtils.md5Hex(password + BaseConstant.DEFAULT_PASSWORD_SALT))
                .build();
        accountEntityRepo.save(accountEntity);
        Token token = new Token();
        token.setRoleCode(String.valueOf(accountEntity.getRoleCode()));
        token.setTimestamp(System.currentTimeMillis());
        token.setUserName(phone);
        String jwtStr = tokenService.createJwtWithSecret(token, accountEntity.getPassword());
        Map<String, Object> jwtMap = new HashMap<>();
        jwtMap.put("token", jwtStr);
        baseResult.returnWithValue(Code.SUCCESS, jwtMap);
        return baseResult;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public BaseResult registerWithMail(String mail, String password, int roleCode) throws TokenException {
        BaseResult baseResult = new BaseResult();
        if (!ObjectUtils.isEmpty(accountEntityRepo.findByEmail(mail))) {
            baseResult.returnWithoutValue(Code.ACCOUNT_ALREADY_EXIST);
            return baseResult;
        }


        AccountEntity accountEntity = AccountEntity.builder().
                email(mail)
                .roleCode(roleCode)
                .password(DigestUtils.md5Hex(password + BaseConstant.DEFAULT_PASSWORD_SALT))
                .build();
        accountEntityRepo.save(accountEntity);
        Token token = new Token();
        token.setRoleCode(String.valueOf(accountEntity.getRoleCode()));
        token.setTimestamp(System.currentTimeMillis());
        token.setUserName(mail);
        String jwtStr = tokenService.createJwtWithSecret(token, accountEntity.getPassword());
        Map<String, Object> jwtMap = new HashMap<>();
        jwtMap.put("token", jwtStr);
        baseResult.returnWithValue(Code.SUCCESS, jwtMap);
        return baseResult;
    }

    @Override
    public BaseResult login(String userName, String password) {
        return null;
    }


    //图形验证码
    @Override
    public BaseResult graphCodeCreate(HttpSession session, HttpServletResponse response) {
        BaseResult baseResult = new BaseResult();

        //设置相应类型,输出内容为图片
        response.setContentType("image/jpeg");
        //设置响应头信息，不缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            String verifyCode = VerifyCodeUtils.outputVerifyImage(220, 76, response.getOutputStream(), 4);
            session.removeAttribute(GRAPHCODEKEY);
            session.setAttribute(GRAPHCODEKEY, verifyCode);
            baseResult.returnWithoutValue(Code.SUCCESS);
        } catch (IOException e) {
            logger.error("获取图片验证码失败", e);
            baseResult.returnWithoutValue(Code.GRAPH_CODE_CREATE_ERROR);
        }
        return baseResult;
    }
    @Override
    public BaseResult graphCodeVerify(HttpSession session, String inputCode) {
        BaseResult baseResult = new BaseResult();
        if (businessConstant.debug) {
            baseResult.returnWithoutValue(Code.DEBUG);
            return baseResult;
        }
        String random = (String) session.getAttribute(GRAPHCODEKEY);
        if (StringUtils.isEmpty(random)) {
            logger.info("session中无验证码");
        }
        if (!StringUtils.equalsIgnoreCase(random, inputCode)) {
            baseResult.returnWithoutValue(Code.GRAPH_CODE_VERIFY_ERROR);
            return baseResult;
        }
        baseResult.returnWithoutValue(Code.SUCCESS);
        return baseResult;

    }

    //手机验证码
    @Override
    public BaseResult phoneVerifyCodeSend(String type, String phone) {
        BaseResult baseResult = new BaseResult();
//        String tplPhoneCode = ConstantFactory.get("phoneCode", "type", type).getCode();
        String key = keyInit("@", type, phone);
        SmsCode smsCode = smsCodeMap.get(key);
        long curTimestamp = System.currentTimeMillis();
        // 验证码获取太频繁
        if (!ObjectUtils.isEmpty(smsCode) && curTimestamp < smsCode.getReSendTime()) {
            baseResult.returnWithoutValue(Code.PHONE_CODE_CREATE_FREQUENT);
            return baseResult;
        }
        smsCode = createSmsCode();
        String codeStr = smsCode.getCode();
        //TODO  发送验证码
        Code code = sendPhoneCode(phone, codeStr);
        if (code == Code.SUCCESS) {
            smsCodeMap.put(key, smsCode);
        }
        baseResult.returnWithValue(code, smsCode);
        return baseResult;
    }
    @Override
    public BaseResult phoneVerifyCodeVerify(String type, String phone, String inputCode) {
        BaseResult baseResult = new BaseResult();
        if (businessConstant.debug) {
            baseResult.returnWithoutValue(Code.DEBUG);
            return baseResult;
        }
        String key = keyInit("@", type, phone);
        SmsCode smsCode = smsCodeMap.get(key);
        if (ObjectUtils.isEmpty(smsCode)) {
            baseResult.returnWithoutValue(Code.PHONE_CODE_VERIFY_ERROR);
            return baseResult;
        }
        String codeStr = smsCode.getCode();
        long curTimestamp = System.currentTimeMillis();
        if (curTimestamp > smsCode.getValidTime()) {
            baseResult.returnWithoutValue(Code.PHONE_CODE_EXPIRE_TIME);
            return baseResult;
        }
        if (!StringUtils.equals(codeStr, inputCode)) {
            baseResult.returnWithoutValue(Code.PHONE_CODE_VERIFY_ERROR);
            return baseResult;
        }
        AccountEntity userEntity = accountEntityRepo.findByPhone(phone);
        if (ObjectUtils.isEmpty(userEntity)) {
            baseResult.returnWithoutValue(Code.USER_NOT_EXIST);
            return baseResult;
        }
        baseResult.returnWithoutValue(Code.SUCCESS);
        return baseResult;
    }

    //邮箱验证码
    @Override
    public BaseResult emailVerifyCodeSend(String type, String email) {
        return null;
    }
    @Override
    public BaseResult emailVerifyCodeVerify(String type, String email, String inputCode) {
        return null;
    }


    private static String keyInit(String separator, String... param) {
        if (param.length == 0) return null;
        StringBuilder ret = new StringBuilder(param.length * 2);
        for (String item : param) {
            ret.append(item).append(separator);
        }
        ret.deleteCharAt(ret.length() - 1);
        return ret.toString();
    }

    protected SmsCode createSmsCode() {
        String code = RandomCodeUtil.getRandomNumCode(6);
        long createTime = System.currentTimeMillis();
        long reSendTime = DateUtil.timestampSecondAfter(createTime, businessConstant.SMS_CODE_RESEND);
        long validTime = DateUtil.timestampSecondAfter(createTime, businessConstant.SMS_CODE_VALID);
        SmsCode smsCode = new SmsCode(code, createTime, reSendTime, validTime);

        return smsCode;
    }

    //TODO SMS服务
    private Code sendPhoneCode(String phone, String smsCode) {

//        Map<String, String> params = new HashMap<>();
//        params.put("code", smsCode);
//        String paramStr = JSON.toJSONString(params);
//
//
//        boolean sendSuccess = false;
//        int count = 0;
//
//        while (!sendSuccess && count < businessConstant.PHONE_CODE_RESEND) {
//            count++;
//            SendSmsResponse sendSmsResponse = SMSUtil.sendSms(phone, SMSUtil.XIMEI_SIGNATURE, tplPhoneCode, paramStr);
//            if (sendSmsResponse.getCode().equals("OK")) {
//                sendSuccess = true;
//                break;
//            }
//            logger.info(JSON.toJSONString(sendSmsResponse));
//        }
//
//
//        if (!sendSuccess) {
//            return Code.PHONE_CODE_SEND_ERROR;
//        }

        return Code.SUCCESS;

    }

}
