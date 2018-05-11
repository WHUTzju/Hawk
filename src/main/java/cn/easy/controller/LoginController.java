package cn.easy.controller;

import cn.easy.business.constant.Code;
import cn.easy.business.exception.TokenException;
import cn.easy.business.response.BaseResult;
import cn.easy.business.response.BaseResultFactory;
import cn.easy.service.LoginService;
import cn.easy.service.impl.LoginServiceImpl;
import cn.easy.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhangrui on 2018/5/8.
 */
@RestController
@RequestMapping("/account")
@Api(value = "注册/登录管理", description = "注册/登录", position = 9)
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    LoginService loginService;

    @ApiOperation(value = "注册", notes = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    BaseResult register(
            @ApiParam("手机号码/邮箱") @RequestParam("phoneMail") @NonNull String phoneMail,
            @ApiParam("验证码") @RequestParam("verifyCode") @NonNull String verifyCode,
            @ApiParam("密码") @RequestParam("password_one") @NonNull String password_one,
            @ApiParam("确认密码") @RequestParam("password_twice") @NonNull String password_twice,
            @ApiParam("角色") @RequestParam("roleCode") @NonNull int roleCode,
            @ApiParam("图形验证码") @RequestParam("graphCode") @NonNull String graphCode,
            HttpServletRequest request, HttpServletResponse response, HttpSession session
    ) throws TokenException {
        BaseResult baseResult = new BaseResult();

        if (!StringUtils.equals(password_one, password_twice)) {
            baseResult.returnWithoutValue(Code.PASSWORD_NOT_EQUAL);
            return baseResult;
        }
        //校验图形验证码
        baseResult = loginService.graphCodeVerify(session, graphCode);
        if (baseResult.getCode() != Code.SUCCESS.getCode()) {
            return baseResult;
        }

        //判断手机注册还是邮箱注册 并进行格式校验、验证码校验、注册
        if (phoneMail.contains("@")) {
            //邮箱格式校验
            if (!CommonUtil.checkEmail(phoneMail)) {
                baseResult.returnWithoutValue(Code.PHONE_FORMAT_ERROR);
                logger.error("邮箱或手机号码格式不对");
                return baseResult;
            }
            baseResult = loginService.emailVerifyCodeVerify("register", phoneMail, verifyCode);
            if (baseResult.getCode() != Code.SUCCESS.getCode()) {
                logger.error("验证码校验错误" + baseResult.getMessage());
                return baseResult;
            }
            //注册
            baseResult = loginService.registerWithMail(phoneMail, password_one, roleCode);
        } else {
            //手机号码格式校验
            if (!CommonUtil.checkPhone(phoneMail)) {
                baseResult.returnWithoutValue(Code.PHONE_FORMAT_ERROR);
                logger.error("邮箱或手机号码格式不对");
                return baseResult;
            }
            baseResult = loginService.phoneVerifyCodeVerify("register", phoneMail, verifyCode);
            if (baseResult.getCode() != Code.SUCCESS.getCode()) {
                logger.error("验证码校验错误" + baseResult.getMessage());
                return baseResult;
            }
            //注册
            baseResult = loginService.registerWithPhone(phoneMail, password_one, roleCode);
        }

        return baseResult;
    }

    @ApiOperation(value = "生成图形验证码", notes = "生成图形验证码")
    @RequestMapping(value = "/getGraphCode", method = RequestMethod.GET)
    BaseResult getGraphCode(
            HttpSession session, HttpServletResponse response) {
        return loginService.graphCodeCreate(session, response);
    }

    @ApiOperation(value = "发送手机或邮箱验证码", notes = "发送手机或邮箱验证码")
    @RequestMapping(value = "/getPhoneCode", method = RequestMethod.POST)
    BaseResult getPhoneCode(
            @ApiParam(value = "验证码类型，register:注册; authCheck:身份校验; modifyPWD:修改密码", required = true) @RequestParam("type") String type,
            @ApiParam(value = "邮箱/手机号码", required = true) @RequestParam("phoneMail") String phoneMail) throws IOException {
        BaseResult baseResult = new BaseResult();
        //判断手机注册还是邮箱注册 并进行格式校验、验证码发送
        if (phoneMail.contains("@")) {
            //邮箱格式校验
            if (!CommonUtil.checkEmail(phoneMail)) {
                baseResult.returnWithoutValue(Code.PHONE_FORMAT_ERROR);
                logger.error("邮箱或手机号码格式不对");
                return baseResult;
            }
            baseResult = loginService.emailVerifyCodeSend(type, phoneMail);
        } else {
            //手机号码格式校验 TODO 区分国际/港澳台手机号码，并提供国际港澳台消息服务
            if (!CommonUtil.checkPhone(phoneMail)) {
                baseResult.returnWithoutValue(Code.PHONE_FORMAT_ERROR);
                logger.error("邮箱或手机号码格式不对");
                return baseResult;
            }
            baseResult = loginService.phoneVerifyCodeSend(type, phoneMail);
        }
        return baseResult;
    }

    @ApiOperation(value = "【TEST】校验手机或邮箱验证码", notes = "校验手机或邮箱验证码")
    @RequestMapping(value = "/checkVerifyCode", method = RequestMethod.POST)
    BaseResult checkVerifyCode(
            @ApiParam(value = "验证码类型，register:注册; authCheck:身份校验; modifyPWD:修改密码", required = true) @RequestParam("type") String type,
            @ApiParam(value = "邮箱/手机号码", required = true) @RequestParam("phoneMail") String phoneMail,
            @ApiParam(value = "验证码", required = true) @RequestParam("verifyCode") @NonNull String verifyCode) throws IOException {
        BaseResult baseResult = new BaseResult();
        //判断手机注册还是邮箱注册 并进行格式校验、验证码校验
        if (phoneMail.contains("@")) {
            //邮箱格式校验
            if (!CommonUtil.checkEmail(phoneMail)) {
                baseResult.returnWithoutValue(Code.PHONE_FORMAT_ERROR);
                logger.error("邮箱或手机号码格式不对");
                return baseResult;
            }
            baseResult = loginService.emailVerifyCodeVerify(type, phoneMail, verifyCode);
        } else {
            //手机号码格式校验
            if (!CommonUtil.checkPhone(phoneMail)) {
                baseResult.returnWithoutValue(Code.PHONE_FORMAT_ERROR);
                logger.error("邮箱或手机号码格式不对");
                return baseResult;
            }
            baseResult = loginService.phoneVerifyCodeVerify(type, phoneMail, verifyCode);
        }
        return baseResult;

    }


}
