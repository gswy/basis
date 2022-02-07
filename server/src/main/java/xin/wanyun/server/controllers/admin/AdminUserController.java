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
import xin.wanyun.server.entity.AdminUser;
import xin.wanyun.server.iservice.AdminUserIServiceImpl;
import xin.wanyun.server.requests.admin.admin_users.CreateAdminUserRequest;
import xin.wanyun.server.requests.admin.admin_users.UpdateAdminUserRequest;
import xin.wanyun.server.response.MessageResponse;
import xin.wanyun.server.response.PaginationResponse;
import xin.wanyun.server.service.RbacService;
import xin.wanyun.server.utils.BCrypt;

import java.util.List;

@RestController
@RequestMapping(path = "admin")
public class AdminUserController {

    /**
     * 注入AdminUserMapper
     */
    @Autowired
    private AdminUserIServiceImpl adminUserIService;

    @Autowired
    private RbacService rbacService;

    /**
     * 管理员列表
     */
    @GetMapping("admin_users")
    @Rbac(guard = "admin", permission = "管理员分页")
    @LogUtil(message = "管理员列表")
    public ResponseEntity<Object> index(Long page, Long size, String username) {
        // 条件构造器
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("created_at");
        if (username != null && !username.equals("")) {
            wrapper.eq("username", username);
        }

        // 分页构造器
        Page<AdminUser> pageWrapper = new Page<>();
        pageWrapper.setSize(size == null ? 10 : size);
        pageWrapper.setCurrent(page == null ? 1 : page );

        // 分页查询
        Page<AdminUser> data = adminUserIService.page(pageWrapper, wrapper);
        return ResponseEntity.ok(new PaginationResponse(data));
    }

    /**
     * 创建管理员
     */
    @PostMapping("admin_user")
    @Rbac(guard = "admin", permission = "创建管理员")
    @LogUtil(message = "创建管理员")
    public ResponseEntity<Object> create(@Validated @RequestBody CreateAdminUserRequest request) {
        // 创建用户
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(request.getUsername());
        adminUser.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        adminUser.setStatus(request.getStatus());
        if (adminUserIService.save(adminUser)) {
            return ResponseEntity.ok(new MessageResponse("创建成功"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("创建失败"));
    }

    /**
     * 查看管理员
     * @param id ID
     * @return ResponseEntity
     */
    @GetMapping("admin_user/{id}")
    @Rbac(guard = "admin", permission = "查看管理员")
    @LogUtil(message = "查看管理员")
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {
        AdminUser adminUser = adminUserIService.getById(id);
        if (adminUser != null) {
            return ResponseEntity.ok(adminUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("找不到该资源"));
    }

    /**
     * 修改管理员
     * @param id ID
     * @return ResponseEntity
     */
    @PutMapping("admin_user/{id}")
    @Rbac(guard = "admin", permission = "修改管理员")
    @LogUtil(message = "修改管理员")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Validated @RequestBody UpdateAdminUserRequest request) {

        // 条件构造器
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);

        // 查询已有数据
        AdminUser adminUser = adminUserIService.getOne(wrapper, false);
        if (adminUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("找不到该数据"));
        }

        // 判断密码是否提交
        if (!request.getPassword().equals("") && request.getPassword() != null) {
            adminUser.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }
        adminUser.setStatus(request.getStatus());
        if (adminUserIService.update(adminUser, wrapper)) {
            // 修改单条
            return ResponseEntity.ok(new MessageResponse("修改成功"));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("修改失败"));
    }

    /**
     * 删除管理员
     * @param id ID
     * @return ResponseEntity
     */
    @DeleteMapping("admin_user/{id}")
    @Rbac(guard = "admin", permission = "删除管理员")
    @LogUtil(message = "删除管理员")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        // 删除操作
        if (adminUserIService.removeById(id)) {
            return ResponseEntity.ok(new MessageResponse("删除成功"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("找不到该资源"));
    }

    /**
     * 查看管理员已有的角色
     * @param id ID
     * @return ResponseEntity
     */
    @GetMapping("admin_user/{id}/role")
    @Rbac(guard = "admin", permission = "分配角色")
    @LogUtil(message = "查看管理员角色")
    public ResponseEntity<Object> showRole(@PathVariable("id") Long id) {
        List<Long> ids = rbacService.getRole(id);
        return ResponseEntity.ok(ids);
    }

    /**
     * 给管理员分配角色
     * @param id ID
     * @return ResponseEntity
     */
    @PutMapping("admin_user/{id}/role")
    @Rbac(guard = "admin", permission = "分配角色")
    @LogUtil(message = "给管理员分配角色")
    public ResponseEntity<Object> syncRole(@PathVariable("id") Long id, @RequestBody List<Long> ids) {
        rbacService.syncRole(id, ids);
        return ResponseEntity.ok(new MessageResponse("分配成功"));
    }

}
