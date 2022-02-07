package xin.wanyun.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@TableName("admin_users")
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {

    @TableId(type = IdType.AUTO)
    private Long Id;

    @TableField("username")
    private String Username;

    @JsonIgnore
    @TableField("password")
    private String Password;

    @TableField("status")
    private Boolean Status;

    @JsonProperty(value = "created_at")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date CreatedAt;

    @JsonProperty(value = "updated_at")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Date UpdatedAt;
}