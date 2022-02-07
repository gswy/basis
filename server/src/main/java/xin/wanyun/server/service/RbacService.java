package xin.wanyun.server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.wanyun.server.config.WanYunConfig;
import xin.wanyun.server.entity.Permission;
import xin.wanyun.server.entity.Role;
import xin.wanyun.server.entity.RolePermission;
import xin.wanyun.server.entity.UserRole;
import xin.wanyun.server.iservice.PermissionIServiceImpl;
import xin.wanyun.server.iservice.RolePermissionIServiceImpl;
import xin.wanyun.server.iservice.UserRoleIServiceImpl;
import xin.wanyun.server.mapper.AdminUserMapper;
import xin.wanyun.server.mapper.RoleMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Rbac业务类
 */
@Service
public class RbacService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleIServiceImpl userRoleIService;

    @Autowired
    private RolePermissionIServiceImpl rolePermissionIService;

    @Autowired
    private PermissionIServiceImpl permissionIService;

    @Autowired
    private WanYunConfig wanYunConfig;

    /**
     * 更新用户和角色表关系
     *
     * @param uid 用户ID
     * @param ids 角色ID列表
     */
    public void syncRole(Long uid, List<Long> ids) {

        // 1. 查询数据库中用户拥有的权限。
        QueryWrapper<UserRole> wrapper = new QueryWrapper<UserRole>()
                .eq("user_id", uid);
        List<UserRole> userList = userRoleIService.list(wrapper);

        // 2. 数据库中的权限组成数组。
        List<Long> oldIds = userList.stream().map(UserRole::getRoleID).collect(Collectors.toList());
        List<Long> dIds = new ArrayList<>(ids);  // 要删除的ids

        // 3. 数据库中和新数据的差集（数据库要新增的ids）
        ids.removeAll(oldIds);
        if (!ids.isEmpty()) {
            List<UserRole> newData = new ArrayList<>();
            for (Long id : ids) {
                newData.add(new UserRole(uid, id));
            }
            userRoleIService.saveBatch(newData);
        }

        // 4. 新数据和数据库中数据差集（数据库要删除的ids）
        oldIds.removeAll(dIds);
        if (oldIds.isEmpty()) {
            return;
        }
        QueryWrapper<UserRole> delWrapper = new QueryWrapper<>();
        delWrapper.eq("user_id", uid).in("role_id", oldIds);
        userRoleIService.remove(delWrapper);
    }

    /**
     * 根据用户id获取角色
     *
     * @param uid 用户ID
     * @return List<Role> 用户角色列表
     */
    public List<Long> getRole(Long uid) {
        List<UserRole> roleList = userRoleIService.list(new QueryWrapper<UserRole>().eq("user_id", uid));
        return roleList.stream().map(UserRole::getRoleID).collect(Collectors.toList());
    }

    /**
     * 更新角色和用户表关系
     *
     * @param rid 角色ID
     * @param ids 权限ID列表
     */
    public void syncPermission(Long rid, List<Long> ids) {
        // 1. 查询数据库中用户拥有的权限。
        List<RolePermission> permissionList = rolePermissionIService.list(
            new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleID, rid));

        // 2. 数据库中的权限组成数组。
        List<Long> oldIds = permissionList.stream().map(RolePermission::getPermissionID).collect(Collectors.toList());
        List<Long> dIds = new ArrayList<>(ids);  // 要删除的ids

        // 3. 数据库中和新数据的差集（数据库要新增的ids）
        ids.removeAll(oldIds);
        if (!ids.isEmpty()) {
            List<RolePermission> newData = new ArrayList<>();
            for (Long id : ids) {
                newData.add(new RolePermission(rid, id));
            }
            rolePermissionIService.saveBatch(newData);
        }

        // 4. 新数据和数据库中数据差集（数据库要删除的ids）
        oldIds.removeAll(dIds);
        if (oldIds.isEmpty()) {
            return;
        }
        rolePermissionIService.remove(
            new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleID, rid)
                .in(RolePermission::getPermissionID, oldIds)
        );
    }

    /**
     * 根据角色id获取权限
     *
     * @param rid 用户ID
     * @return List<Role> 角色权限列表
     */
    public List<Long> getPermission(Long rid) {
        List<RolePermission> permissionList = rolePermissionIService.list(
            new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleID, rid)
        );
        return permissionList.stream().map(RolePermission::getPermissionID).collect(Collectors.toList());
    }

    /**
     * 判断用户是否有权限
     *
     * @param userID     用户ID
     * @param guard      守卫组
     * @param permission 权限节点
     * @return boolean
     */
    public boolean hasPermission(Long userID, String guard, String permission) {

        // 根据用户ID和守卫查询用户拥有的权限
        List<Role> adminRoles = adminUserMapper.getAdminRole(userID, guard);
        List<String> roleNameArr = adminRoles.stream().map(Role::getName).collect(Collectors.toList());

        // 判断用户是否存在超级管理员权限
        if (roleNameArr.contains(wanYunConfig.getAdmin())) {
            return true;
        }

        // 查询权限列表
        List<Long> roleIdsArr = adminRoles.stream()
                .filter(role -> !Objects.equals(wanYunConfig.getAdmin(), role.getName())).map(Role::getId)
                .collect(Collectors.toList());

        List<Permission> permissions = roleMapper.getRolePermissions(roleIdsArr, guard);
        List<String> permissionNameArr = permissions.stream().map(Permission::getName).collect(Collectors.toList());

        // 判断用户是否有权限
        return permissionNameArr.contains(permission);
    }

    /**
     * 根据用户ID获取拥有的权限
     */
    public List<String> getUserPermission(Long uid) {
        // 判断用户是否为超级管理员
        List<Role> roles = adminUserMapper.getAdminRole(uid, "admin");
        List<String> rolesName = roles.stream().map(Role::getName).collect(Collectors.toList());
        if (rolesName.contains(wanYunConfig.getAdmin())) {
            List<Permission> permissions = permissionIService.list(new QueryWrapper<Permission>()
                    .eq("guard", "admin"));
            return permissions.stream().map(Permission::getName).collect(Collectors.toList());
        }

        // 非超级管理员
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissions = roleMapper.getRolePermissions(roleIds, "admin");
        return permissions.stream().map(Permission::getName).collect(Collectors.toList());
    }
}