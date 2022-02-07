package xin.wanyun.server.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.wanyun.server.aop.annotation.Rbac;
import xin.wanyun.server.entity.AdminUser;
import xin.wanyun.server.exception.RbacException;
import xin.wanyun.server.service.RbacService;
import xin.wanyun.server.thread.AdminUserThread;

/**
 * 自定义rbac管理
 */
@Aspect
@Component
public class RbacAspect {

    @Autowired
    RbacService rbacService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(xin.wanyun.server.aop.annotation.Rbac)")
    public void annotationPointcut() { }

    /**
     * 进入方法前可以实现一些业务逻辑
     */
    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) throws RbacException {

        // 获取注解参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Rbac annotation = signature.getMethod().getAnnotation(Rbac.class);
        AdminUser adminUser = AdminUserThread.get();
        if (adminUser == null) {
            throw new RbacException("无权限操作，请联系管理员！");
        }

        if (! rbacService.hasPermission(adminUser.getId(), annotation.guard(), annotation.permission())) {
            throw new RbacException("无权限操作，请联系管理员！");
        }
    }
}


