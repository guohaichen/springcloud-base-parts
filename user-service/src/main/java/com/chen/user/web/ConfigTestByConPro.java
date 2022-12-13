package com.chen.user.web;

import com.chen.user.config.NacosConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cgh
 * @create 2022-12-13 9:19
 * 测试Nacos配置中心 热更新的其中方式之二：@ConfigurationProperties
 */
@RestController
@RequestMapping("/config2")
public class ConfigTestByConPro {

    @Autowired
    private NacosConfig nacosConfig;

    @GetMapping("/gender")
    public String getGender(){
        return nacosConfig.getGender();
    }

    @GetMapping("/lastname")
    public String getLastName(){
        return nacosConfig.getLastName();
    }
}
