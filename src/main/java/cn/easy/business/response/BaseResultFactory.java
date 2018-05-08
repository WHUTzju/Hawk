package cn.easy.business.response;

import cn.easy.business.constant.Code;

/**
 * Created by superlee on 2017/11/7.
 * baseResult工程方法
 */
@SuppressWarnings("unchecked")
public final class BaseResultFactory {


    public static BaseResult produceEmptyResult(Code code) {
        return new BaseResult(code);
    }

    public static BaseResult produceEmptyResult(int codeInt, String msg) {
        return new BaseResult(codeInt, msg);
    }

    public static BaseResult produceResult(int codeInt, String msg, Object data) {
        return new BaseResult(codeInt, msg, data);
    }

    public static BaseResult produceResult(Code code, Object data) {
        return new BaseResult(code.getCode(), code.getMsg(), data);
    }


}
