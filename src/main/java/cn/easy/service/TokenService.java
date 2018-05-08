package cn.easy.service;


import cn.easy.business.exception.TokenException;
import cn.easy.model.Token;

/**
 * Created by superlee on 2018/1/8.
 */
public interface TokenService {

    String createJwtWithSecret(Token token, String secret) throws TokenException;

    Token verifyJwt(String jwtStr,String secret) throws TokenException;

    Token getTokenWithoutSig(String jwtStr) throws TokenException;
}
