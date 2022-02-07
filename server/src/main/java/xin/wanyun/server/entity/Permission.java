package xin.wanyun.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 权限实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("permissions")
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long Id;

    @TableField("name")
    private String Name;

    @TableField("parent_id")
    private Long ParentId;

    @TableField("guard")
    private String Guard;

}
