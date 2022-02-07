package xin.wanyun.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xin.wanyun.server.entity.AdminUser;
import xin.wanyun.server.entity.Permission;
import xin.wanyun.server.entity.Role;

import java.util.List;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdminUser> {

    /**
     * 查询用户拥有的角色
     */
    List<Role> getAdminRole(@Param("user_id") Long userId,
                            @Param("guard") String guard);

    /**
     * 根据用户id查找
     */
    List<Permission> getAdminPermission(@Param("user_id") Long userId,
                                        @Param("guard") String guard);
}
