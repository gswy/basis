package xin.wanyun.server.controllers.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.wanyun.server.aop.annotation.LogUtil;
import xin.wanyun.server.entity.AdminUser;
import xin.wanyun.server.iservice.AdminUserIServiceImpl;
import xin.wanyun.server.requests.admin.admin_users.UpdateAdminUserRequest;
import xin.wanyun.server.response.MessageResponse;
import xin.wanyun.server.thread.AdminUserThread;
import xin.wanyun.server.utils.BCrypt;

@RestController
@RequestMapping(path = "admin")
public class MineController {

    @Autowired
    private AdminUserIServiceImpl userIService;

    @PatchMapping("/mine")
    @LogUtil(message = "修改当前登录管理员信息")
    public ResponseEntity<Object> update(@RequestBody UpdateAdminUserRequest request) {

        AdminUser adminUser = AdminUserThread.get();
        if (!request.getPassword().isEmpty()) {
            adminUser.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        if (userIService.update(adminUser,
                new LambdaQueryWrapper<AdminUser>().eq(AdminUser::getId, adminUser.getId()))) {
            return ResponseEntity.ok().body(new MessageResponse("保存成功"));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("保存失败"));
    }

}
