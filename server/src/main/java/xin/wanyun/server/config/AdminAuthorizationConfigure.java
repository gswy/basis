package xin.wanyun.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xin.wanyun.server.handler.AdminInterceptor;

/**
 * 后台拦截
 */
@Configuration
public class AdminAuthorizationConfigure implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 需要排除要拦截的接口
        String[] excludePath = {"/admin/login"};

        // 配置拦截器
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")           // 需要拦截的接口路径
                .excludePathPatterns(excludePath);      // 需要排除的接口路径

    }
}
