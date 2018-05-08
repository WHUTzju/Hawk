package cn.easy.interceptor.token;

import cn.easy.business.constant.BaseConstant;
import cn.easy.model.Token;
import cn.easy.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 *
 * @author superlee
 * @date 2018/1/8
 */
@Component
@Order(3)
public class RequestTokenArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Token.class)
                && parameter.hasParameterAnnotation(RequestToken.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String jwtStr = webRequest.getHeader(BaseConstant.X_ACCESS_TOKEN);

        Token token = tokenService.verifyJwt(jwtStr);
        return token;

    }


}
