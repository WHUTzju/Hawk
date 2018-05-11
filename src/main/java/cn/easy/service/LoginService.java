package cn.easy.service;

import cn.easy.business.exception.TokenException;
import cn.easy.business.response.BaseResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by zhangrui on 2018/5/7.
 */
public interface LoginService {
    //手机号码注册
    BaseResult registerWithPhone(String phone,String password,int roleCode) throws TokenException;
    //邮箱注册
    BaseResult registerWithMail(String mail,String password,int roleCode) throws TokenException;
    BaseResult login(String userName,String password);
    //获取验证码
//    BaseResult getSecurityCode(String phoneMail);

    //获取图形验证码
    BaseResult graphCodeCreate(HttpSession session, HttpServletResponse response);
    //校验图形验证码
    BaseResult graphCodeVerify(HttpSession session,String inputCode);

    //发送短信验证码
    BaseResult phoneVerifyCodeSend(String type, String phone) ;
    //验证短信验证码
    BaseResult phoneVerifyCodeVerify(String type,String phone,String inputCode);

    //发送邮箱验证码
    BaseResult emailVerifyCodeSend(String type, String email) throws IOException;
    //验证邮箱验证码
    BaseResult emailVerifyCodeVerify(String type,String email,String inputCode);
}
