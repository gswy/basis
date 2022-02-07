package xin.wanyun.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("roles_has_permissions")
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {

    /**
     * role_id
     */
    @TableField("role_id")
    private Long RoleID;

    /**
     * 关联permission_id
     */
    @TableField("permission_id")
    private Long PermissionID;
}
