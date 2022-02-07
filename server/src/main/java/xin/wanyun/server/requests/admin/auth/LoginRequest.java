package xin.wanyun.server.requests.admin.auth;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {

    @NotBlank(message = "账号不能为空")
    @Length(min = 5, max = 20, message = "账号长度为5~20位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 5, max = 20, message = "密码长度为5~20位")
    private String password;
}
