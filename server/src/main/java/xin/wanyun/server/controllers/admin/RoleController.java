package xin.wanyun.server.controllers.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.wanyun.server.aop.annotation.LogUtil;
import xin.wanyun.server.aop.annotation.Rbac;
import xin.wanyun.server.entity.Role;
import xin.wanyun.server.iservice.RoleIServiceImpl;
import xin.wanyun.server.requests.admin.role.CreateRoleRequest;
import xin.wanyun.server.requests.admin.role.UpdateRoleRequest;
import xin.wanyun.server.response.MessageResponse;
import xin.wanyun.server.response.PaginationResponse;
import xin.wanyun.server.service.RbacService;
import java.util.List;

@RestController
@RequestMapping(path = "admin")
public class RoleController {

    /**
     * 注入RoleIServiceImpl
     */
    @Autowired
    private RoleIServiceImpl roleIService;

    /**
     * 注入rbacService
     */
    @Autowired
    private RbacService rbacService;

    /**
     * 分页列表
     */
    @GetMapping("roles")
    @Rbac(guard = "admin", permission = "角色列表")
    @LogUtil(message = "查看角色列表")
    public ResponseEntity<Object> index(Long page, Long size) {
        // 条件构造器
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("guard", "admin");

        // 分页构造器
        Page<Role> pageWrapper = new Page<>();
        pageWrapper.setSize(size == null ? 10 : size);
        pageWrapper.setCurrent(page == null ? 1 : page );

        // 分页查询
        Page<Role> data = roleIService.page(pageWrapper, wrapper);

        return ResponseEntity.ok(new PaginationResponse(data));
    }

    /**
     * 创建
     */
    @PostMapping("role")
    @Rbac(guard = "admin", permission = "创建角色")
    @LogUtil(message = "创建角色")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateRoleRequest request) {
        Role data = new Role();
        data.setGuard("admin");
        data.setName(request.getName());
        if (roleIService.save(data)) {
            return ResponseEntity.ok(new MessageResponse("创建成功"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("创建失败"));
    }

    /**
     * 查看
     * @param id ID
     * @return ResponseEntity
     */
    @GetMapping("role/{id}")
    @Rbac(guard = "admin", permission = "查看角色")
    @LogUtil(message = "查看角色详情")
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {
        // 查询单条
        Role Role = roleIService.getById(id);
        if (Role != null) {
            return ResponseEntity.ok(Role);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("找不到该资源"));
    }

    /**
     * 修改
     * @param id ID
     * @return ResponseEntity
     */
    @PutMapping("role/{id}")
    @Rbac(guard = "admin", permission = "修改角色")
    @LogUtil(message = "修改角色信息")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Validated @RequestBody UpdateRoleRequest request) {
        Role data = roleIService.getById(id);
        data.setName(request.getName());
        if (roleIService.updateById(data)) {
            return ResponseEntity.ok(new MessageResponse("修改成功"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("找不到该资源"));
    }

    /**
     * 删除
     * @param id ID
     * @return ResponseEntity
     */
    @DeleteMapping("role/{id}")
    @Rbac(guard = "admin", permission = "删除角色")
    @LogUtil(message = "删除角色")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        if (roleIService.removeById(id)) {
            return ResponseEntity.ok(new MessageResponse("删除成功"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("找不到该资源"));
    }

    /**
     * 角色列表
     */
    @GetMapping("role/list")
    @Rbac(guard = "admin", permission = "角色下拉")
    @LogUtil(message = "获取角色列表")
    public ResponseEntity<Object> list() {
        // 条件构造器
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("guard", "admin");
        List<Role> data = roleIService.list(wrapper);    // 查询
        return ResponseEntity.ok(data);
    }

    /**
     * 查看角色已有的权限
     * @param id ID
     * @return ResponseEntity
     */
    @GetMapping("role/{id}/permission")
    @Rbac(guard = "admin", permission = "分配权限")
    @LogUtil(message = "查看角色已拥有的权限")
    public ResponseEntity<Object> showRole(@PathVariable("id") Long id) {
        List<Long> ids = rbacService.getPermission(id);
        return ResponseEntity.ok(ids);
    }

    /**
     * 给角色分配权限
     * @param id ID
     * @return ResponseEntity
     */
    @PutMapping("role/{id}/permission")
    @Rbac(guard = "admin", permission = "分配权限")
    @LogUtil(message = "给角色分配权限")
    public ResponseEntity<Object> syncRole(@PathVariable("id") Long id, @RequestBody List<Long> ids) {
        rbacService.syncPermission(id, ids);
        return ResponseEntity.ok(new MessageResponse("权限分配成功"));
    }
}
