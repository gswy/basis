package xin.wanyun.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 角色实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("roles")
public class Role {

    @TableId(type = IdType.AUTO)
    private Long Id;

    @TableField("name")
    private String Name;

    @TableField("guard")
    private String Guard;

    @JsonProperty(value = "created_at")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date CreatedAt;

    @JsonProperty(value = "updated_at")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Date UpdatedAt;

}
