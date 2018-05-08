package cn.easy.interceptor.token;

import java.lang.annotation.*;

/**
 * Created by superlee on 2018/1/8.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
@Inherited
public @interface RequestToken {

}
