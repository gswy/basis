package xin.wanyun.server.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateResponse {

    /**
     * 基本说明
     */
    private String message = "表单错误";

    /**
     * 详细错误信息
     */
    private Map<String, String> errors = new HashMap<>();

    /**
     * 构造错误数据
     *
     * @param bindingResult 未通过的表单实例
     */
    public ValidateResponse(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
    }
}
