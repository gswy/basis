package xin.wanyun.server.controllers.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xin.wanyun.server.aop.annotation.LogUtil;
import xin.wanyun.server.aop.annotation.Rbac;
import xin.wanyun.server.entity.Permission;
import xin.wanyun.server.iservice.PermissionIServiceImpl;
import xin.wanyun.server.response.entity.PermissionResponse;
import xin.wanyun.server.response.entity.PermissionTreeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "admin")
public class PermissionController {

    /**
     * 注入PermissionMapper
     */
    @Autowired
    private PermissionIServiceImpl service;

    /**
     * 分页列表
     */
    @GetMapping("permission/tree")
    @Rbac(guard = "admin", permission = "权限树")
    @LogUtil(message = "查看权限列表")
    public ResponseEntity<Object> index() {
        // 顶级权限查询
        QueryWrapper<Permission> parentWrapper = new QueryWrapper<Permission>()
                .eq("guard", "admin").eq("parent_id", 0).orderByAsc("id");
        List<Permission> parentPermissions = service.list(parentWrapper);

        // 子内容查询
        List<Long> parentIds = parentPermissions.stream().map(Permission::getId).collect(Collectors.toList());
        QueryWrapper<Permission> wrapper = new QueryWrapper<Permission>()
                .eq("guard", "admin").in("parent_id", parentIds).orderByAsc("id");
        List<Permission> permissions = service.list(wrapper);

        // 定义返回内容，处理查到的数据
        List<PermissionTreeResponse> result = new ArrayList<>();
        for (Permission parentPermission: parentPermissions) {

            List<PermissionResponse> childPermission = new ArrayList<>();
            for (Permission permission : permissions) {
                if (Objects.equals(parentPermission.getId(), permission.getParentId())) {
                    childPermission.add(new PermissionResponse(permission.getId(), permission.getName()));
                }
            }
            result.add(new PermissionTreeResponse(parentPermission.getId(), parentPermission.getName(), childPermission));
        }

        return ResponseEntity.ok(result);
    }
}
