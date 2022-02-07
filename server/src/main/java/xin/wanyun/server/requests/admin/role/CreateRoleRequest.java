package xin.wanyun.server.requests.admin.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CreateRoleRequest implements Serializable {

    @NotBlank(message = "名称不能为空")
    private String name;
}