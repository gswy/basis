package xin.wanyun.server.aop.annotation;

import xin.wanyun.server.validator.unique.UniqueValidateAspect;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

/**
 * 自定义数据库唯一验证
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueValidateAspect.class})
public @interface UniqueValidate {

    /**
     * 实体类
     */
    String tableName();

    /**
     * 字段名
     */
    String fieldName();

    /**
     * 错误信息
     */
    String message() default "";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
