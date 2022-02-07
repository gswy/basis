package xin.wanyun.server.validator.phone;

import xin.wanyun.server.aop.annotation.PhoneValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号自定义验证
 */
public class PhoneValidateAspect implements ConstraintValidator<PhoneValidate, String> {

    private PhoneValidate phoneValidate;

    @Override
    public void initialize(PhoneValidate constraintAnnotation) {
        this.phoneValidate = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern compile = Pattern.compile("^1\\d{10}$");
        Matcher matcher = compile.matcher(value);
        return matcher.matches();
    }
}
