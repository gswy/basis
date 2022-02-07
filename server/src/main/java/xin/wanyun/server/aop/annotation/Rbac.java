package xin.wanyun.server.aop.annotation;

import java.lang.annotation.*;

/**
 * RBAC实现类
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Rbac {

    /**
     * 守卫名称
     */
    String guard() default "";

    /**
     * 权限
     */
    String permission() default "";
}
