package xin.wanyun.server.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("users_has_roles")
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    /**
     * 关联user_id
     */
    @TableField("user_id")
    private Long UserID;

    /**
     * 关联role_id
     */
    @TableField("role_id")
    private Long RoleID;
}
