package cn.easy.util;

import cn.easy.business.exception.TokenException;
import cn.easy.model.Token;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by superlee on 2017/11/7.
 * token工具类
 */
public final class TokenUtil {

    private static final Logger logger = Logger.getLogger(TokenUtil.class);

    /**
     * DES加密token属性
     *
     * @param pubKey      公钥
     * @param roleCode    角色码
     * @param clientId    客户端id
     * @param authVersion 权限版本
     * @param encryptKey  加密密钥
     * @return 加密字符串
     * @throws TokenException token异常
     */
    public static synchronized String encryptToken(String pubKey, String roleCode, String clientId, String authVersion, String... encryptKey) throws TokenException {
        long timestamp = System.currentTimeMillis();

        Token token = new Token();
        token.setTimestamp(timestamp);
        token.setRoleCode(roleCode);
        return encryptToken(token, encryptKey);
    }

    /**
     * DES加密token实体
     *
     * @param token      token实体
     * @param encryptKey 密钥
     * @return 加密字符串
     * @throws TokenException token异常
     */
    public static String encryptToken(Token token, String... encryptKey) throws TokenException {

        String tokenJson = JSON.toJSONString(token);
        String encryptTokenStr;

        try {
            encryptTokenStr = DesUtils.encryptToken(tokenJson, encryptKey);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("token字符串加密异常");
            throw new TokenException("token字符串加密异常");
        }
        return encryptTokenStr;
    }

    /**
     * DES解密token字符串
     *
     * @param encryptTokenStr 待解密字符串
     * @param key             解密密钥
     * @return token实体
     * @throws TokenException token异常
     */
    public static synchronized Token decryptToken(String encryptTokenStr, String... key) throws TokenException {
        try {
            String tokenStr = DesUtils.decryptToken(encryptTokenStr, key);
            return JSON.parseObject(tokenStr, Token.class);
        } catch (BadPaddingException bpe) {
            bpe.printStackTrace();
            logger.error("token解析密钥错误");
            throw new TokenException("token解析密钥错误");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("token解析异常");
            throw new TokenException("token解析异常");
        }

    }

    /**
     * 从request中获取存在于cookie中的token字符串
     *
     * @param httpServletRequest request对象
     * @return token字符串
     */
    public static String getTokenStrFromRequest(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        //cookie中无token
        if (ObjectUtils.isEmpty(cookies)) return null;

        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
                break;
            }
        }
        return token;

    }

    /**
     * 从request中获取存在于cookie中的token实体
     *
     * @param httpServletRequest request对象
     * @return token实体
     */
    public static Token getTokenObjFromRequest(HttpServletRequest httpServletRequest, String... key) throws TokenException {
        String tokenStr = getTokenStrFromRequest(httpServletRequest);
        if (StringUtils.isEmpty(tokenStr)) return null;
        Token token = decryptToken(tokenStr, key);
        return token;
    }


    /**
     * 设置token到cookie中
     *
     * @param token      token字符串
     * @param isHttpOnly cookie是否只允许http可读
     * @param request    请求对象
     * @param response   请求返回对象
     */
    public static void setTokenToCookie(String token, boolean isHttpOnly, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        //cookie中无token
        if (ObjectUtils.isEmpty(cookies)) {
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(isHttpOnly);
            response.addCookie(cookie);
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                cookie.setValue(token);
                cookie.setPath("/");
                cookie.setHttpOnly(isHttpOnly);
                response.addCookie(cookie);
            }
        }
    }


    public static void setTokenToResponse(String token, HttpServletResponse response) {
        response.setHeader("token", token);
    }


}
