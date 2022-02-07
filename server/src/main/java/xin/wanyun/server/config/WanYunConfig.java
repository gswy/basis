package xin.wanyun.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rbac")
@PropertySource(value = {"classpath:wanyun.properties"}, encoding = "utf-8")
public class WanYunConfig {

    /**
     * 超级管理员角色名称。
     * 只要配置此处的超级管理员名称，用户只要是拥有此角色，将一律放行。
     * 即最高管理员权限。
     */
    private String admin;

}
