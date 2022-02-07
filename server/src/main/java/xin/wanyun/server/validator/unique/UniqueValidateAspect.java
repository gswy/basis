package xin.wanyun.server.validator.unique;

import org.springframework.beans.factory.annotation.Autowired;
import xin.wanyun.server.aop.annotation.UniqueValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义实现唯一验证
 */
public class UniqueValidateAspect implements ConstraintValidator<UniqueValidate, String> {

    private UniqueValidate uniqueValidate;

    @Autowired
    private UniqueMapper uniqueMapper;

    @Override
    public void initialize(UniqueValidate constraintAnnotation) {
        this.uniqueValidate = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Object obj = uniqueMapper.uniqueRecord(uniqueValidate.tableName(), uniqueValidate.fieldName(), value);
            return obj == null;
        } catch (Exception e) {
            return false;
        }
    }
}

