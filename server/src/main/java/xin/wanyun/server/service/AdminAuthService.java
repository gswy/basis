package xin.wanyun.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xin.wanyun.server.entity.AdminUser;
import xin.wanyun.server.iservice.AdminUserIServiceImpl;
import xin.wanyun.server.response.JwtTokenResponse;
import xin.wanyun.server.response.MessageResponse;
import xin.wanyun.server.utils.BCrypt;
import xin.wanyun.server.utils.JwtUtil;

@Service
public class AdminAuthService {

    @Autowired
    private AdminUserIServiceImpl adminUserIService;

    /**
     * 登录类
     */
    public ResponseEntity<Object> loginWithPassword(String username, String password) {
        // 查询用户
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<AdminUser>();
        wrapper.eq("username", username)
                .eq("status", true);
        AdminUser user = adminUserIService.getOne(wrapper, false);

        // 验证密码
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("用户名或者密码错误"));
        }

        // 生成Token
        String token = JwtUtil.createToken(user.getId());
        token = "Bearer " + token;

        return ResponseEntity.ok().body(new JwtTokenResponse(token, JwtUtil.ExpireTime, "登录成功"));
    }

    /**
     * 查找用户
     */
    public AdminUser findById(Long id) {
        if (id == null) return null;
        // 查询用户
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<AdminUser>();
        wrapper.eq("id", id)
                .eq("status", true);
        return adminUserIService.getOne(wrapper, false);
    }
}
