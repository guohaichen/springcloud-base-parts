package com.chen.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author cgh
 * @create 2022-12-13 9:26
 * 测试Nacos配置中心 热更新的其中方式之二：@ConfigurationProperties,
 * 这里的属性gender就和yml中以chen为prefix开头的配置绑定
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "chen")
public class NacosConfig {
    private String Gender;

    private String lastName;
}
