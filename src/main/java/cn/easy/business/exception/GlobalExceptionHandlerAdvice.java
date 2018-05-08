package cn.easy.business.exception;

import cn.easy.business.constant.Code;
import cn.easy.business.response.BaseResult;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * Created by superlee on 2017/11/6.
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandlerAdvice.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public BaseResult handleTokenException(ConstraintViolationException e) {
        logger.error(e);
        e.printStackTrace();
        return new BaseResult(Code.PARAMETER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public BaseResult handleBusinessException(BusinessException be) {
        logger.error(be);
        be.printStackTrace();
        return be.getBaseResult();
    }


    @ExceptionHandler(TokenException.class)
    @ResponseBody
    public BaseResult handleTokenException(TokenException e) {
        logger.error(e);
        e.printStackTrace();
        return new BaseResult(Code.TOKEN_CRYPT_ERROR.getCode(), e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResult handleDefaultException(Exception be) {
        logger.error(be);
        be.printStackTrace();
        return new BaseResult(Code.UNKNOWN_ABNORMAL.getCode(), Code.UNKNOWN_ABNORMAL.getMsg(), be.getMessage());
    }


}
