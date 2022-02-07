package xin.wanyun.server.requests.admin.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdateRoleRequest implements Serializable {

    @NotBlank(message = "账号不能为空")
    private String name;
}