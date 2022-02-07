package xin.wanyun.server.requests.admin.admin_users;

import lombok.Data;
import xin.wanyun.server.aop.annotation.UniqueValidate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CreateAdminUserRequest implements Serializable {

    @NotBlank(message = "账号不能为空")
    @Size(min = 5, max = 20, message = "账号格式错误")
    @UniqueValidate(tableName = "admin_users", fieldName = "username", message = "该账号已存在")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 5, max = 20, message = "密码格式错误")
    private String password;

    private Boolean status;
}