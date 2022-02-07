package xin.wanyun.server.aop.annotation;


import xin.wanyun.server.validator.phone.PhoneValidateAspect;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义手机号验证
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneValidateAspect.class})
public @interface PhoneValidate {



    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}