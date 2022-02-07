package xin.wanyun.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xin.wanyun.server.entity.AdminUser;
import xin.wanyun.server.response.MessageResponse;
import xin.wanyun.server.service.AdminAuthService;
import xin.wanyun.server.thread.AdminUserThread;
import xin.wanyun.server.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminAuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取token
        String token = request.getHeader("Authorization");
        if (token == null) {
            return false;
        }

        // 验证token
        Long sub = JwtUtil.validateToken(token);
        // 查找用户
        AdminUser user = authService.findById(sub);
        if (sub == null || user == null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(401);
            // 转json
            String msgJson = new ObjectMapper().writeValueAsString(new MessageResponse("Token错误"));
            response.getWriter().print(msgJson);
            return false;
        }

        // 存入线程变量
        AdminUserThread.put(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 如果不删除ThreadLocal的数据，会有内存溢出
        AdminUserThread.remove();
    }
}
