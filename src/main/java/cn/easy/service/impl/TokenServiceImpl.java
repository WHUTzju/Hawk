package cn.easy.service.impl;

import cn.easy.business.constant.BusinessConstant;
import cn.easy.business.exception.TokenException;
import cn.easy.model.Token;
import cn.easy.service.TokenService;
import cn.easy.util.JwtUtils;
import cn.easy.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangrui on 2018/5/7.
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger logger = Logger.getLogger(TokenServiceImpl.class);

    private static final String JWT_ISSUER = "admin";
    private static final String DEFAULT_SECRETE = "admin";

    @Autowired
    private BusinessConstant businessConstant;

    @Override
    public String createJwtWithSecret(Token token, String secret) throws TokenException {
        String tokenEncryptStr = TokenUtil.encryptToken(token);
        if (StringUtils.isEmpty(secret)) {
            logger.info("用户密码为空，采用默认密钥加密");
            secret = DEFAULT_SECRETE;
        }
        // token有效期
        long ttlMillis = businessConstant.TOKEN_VALID_TIME * 60000;
        return JwtUtils.createJWT(JWT_ISSUER, tokenEncryptStr, ttlMillis, secret);
    }

    @Override
    public Token verifyJwt(String jwtStr,String secret) throws TokenException {
        if (StringUtils.isEmpty(jwtStr)) {
            String errMsg = "jwt为空";
            logger.info(errMsg);
            throw new TokenException(errMsg);
        }
        Token token = getTokenWithoutSig(jwtStr);
        logger.info("token解析结果：" + token);
        if (StringUtils.isEmpty(secret)) {
            logger.info("用户密码为空，采用默认密钥加密");
            secret = DEFAULT_SECRETE;
        }
        // 验证签名
        JwtUtils.parseJWT(jwtStr, secret);
        return token;
    }

    @Override
    public Token getTokenWithoutSig(String jwtStr) throws TokenException {
        String tokenStr = getTokenStrWithoutSig(jwtStr);
        Token token = TokenUtil.decryptToken(tokenStr);
        if (ObjectUtils.isEmpty(token)) {
            String errMsg = "token为空";
            logger.info(errMsg);
            throw new TokenException(errMsg);
        }
        return token;
    }
    private static String getTokenStrWithoutSig(String jwt) throws TokenException {
        String[] arr = jwt.split("\\.");
        if (arr.length != 3 || StringUtils.isEmpty(arr[1])) {
            String errMsg = "jwt格式错误";
            logger.info(errMsg);
            throw new TokenException(errMsg);
        }

        String claimStr;

        try {
            claimStr = new String(Base64Utils.decodeFromString(arr[1]), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            String errMsg = "base64解码异常";
            logger.info(errMsg);
            throw new TokenException(errMsg);
        }

        JSONObject jsonObject = JSON.parseObject(claimStr);
        String result = jsonObject.getString(Claims.SUBJECT);
        if (StringUtils.isEmpty(result)) {
            String errMsg = "从jwt获取token加密串失败";
            logger.info(errMsg);
            throw new TokenException(errMsg);
        }
        return result;
    }
}
