package xin.wanyun.server.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xin.wanyun.server.aop.annotation.LogUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义请求日志处理
 */
@Aspect
@Component
public class LogUtilAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 线程局部变量，用于解决多线程中相同变量的访问冲突问题。
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切入点
     */
    @Pointcut("@annotation(xin.wanyun.server.aop.annotation.LogUtil)")
    public void annotationPointcut() {}

    @Before("annotationPointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 请求时间
        Date date = new Date();
        date.setTime(startTime.get());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String requestTime = simpleDateFormat.format(date);

        // 获取注解参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogUtil annotation = signature.getMethod().getAnnotation(LogUtil.class);

        // 接收到请求，记录内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        logger.info("Description: " + annotation.message());
        logger.info("URL: " + request.getRequestURL());
        logger.info("HTTP Method: " + request.getMethod());
        logger.info("IP: " + request.getRemoteAddr());
        logger.info("Request Params: " + request.getQueryString());
        logger.info("请求时间: " + requestTime);
    }

    @AfterReturning(pointcut = "annotationPointcut()", returning = "retObject")
    public void doAfterReturning(Object retObject) throws Throwable {
        // 处理完请求，返回内容
        logger.info("执行时长：" + (System.currentTimeMillis() - startTime.get()) + "毫秒");
    }




}
