package xin.wanyun.server.aop.annotation;

import java.lang.annotation.*;

/**
 * 自定义日志记录类
 *
 * 此类用于定义至Controller上。
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogUtil {

    /**
     * 日志描述
     */
    String message() default "";

}
