package cn.easy.business.exception;

import cn.easy.business.constant.Code;

/**
 * Created by superlee on 2017/11/6.
 * 业务异常
 */
public final class BusinessException extends BaseException {
    public BusinessException() {
    }

    public BusinessException(Code code) {
        super(code.getCode(), code.getMsg());
    }

    public BusinessException(int codeInt, String errorMsg) {
        super(codeInt, errorMsg);
    }
}
