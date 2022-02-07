package xin.wanyun.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xin.wanyun.server.entity.Permission;
import xin.wanyun.server.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色拥有的权限
     */
    List<Permission> getRolePermissions(@Param("role_ids") List<Long> roleIds,
                                        @Param("guard") String guard);

}
