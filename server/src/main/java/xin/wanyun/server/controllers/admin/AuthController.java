package xin.wanyun.server.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.wanyun.server.aop.annotation.LogUtil;
import xin.wanyun.server.entity.AdminUser;
import xin.wanyun.server.mapper.AdminUserMapper;
import xin.wanyun.server.requests.admin.auth.LoginRequest;
import xin.wanyun.server.response.entity.MineResponse;
import xin.wanyun.server.service.AdminAuthService;
import xin.wanyun.server.service.RbacService;
import xin.wanyun.server.thread.AdminUserThread;

import java.util.List;

@RestController
@RequestMapping(path = "admin")
public class AuthController {

    @Autowired
    private AdminAuthService authService;

    @Autowired
    private RbacService rbacService;

    /**
     * 登录控制器
     */
    @PostMapping("login")
    @LogUtil(message = "管理员登录")
    public ResponseEntity<Object> login(@Validated @RequestBody LoginRequest loginRequest) {
        return authService.loginWithPassword(loginRequest.getUsername(), loginRequest.getPassword());
    }

    /**
     * 获取登录用户信息
     */
    @GetMapping("mine")
    @LogUtil(message = "获取登录信息")
    public ResponseEntity<Object> mine() {
        AdminUser adminUser = AdminUserThread.get();
        List<String> permissions = rbacService.getUserPermission(adminUser.getId());
        MineResponse data = new MineResponse(adminUser.getId(), adminUser.getUsername(), permissions);
        return ResponseEntity.ok(data);
    }
}
