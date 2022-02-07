package xin.wanyun.server.requests.admin.admin_users;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateAdminUserRequest implements Serializable {

    private String password;

    private Boolean status;
}