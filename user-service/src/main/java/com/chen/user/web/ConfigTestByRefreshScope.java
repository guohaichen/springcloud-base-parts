package com.chen.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cgh
 * @create 2022-12-12 17:27
 * 测试Nacos配置中心 热更新的其中方式之一：@RefreshScope
 */
@RestController
@Slf4j
//热更新，修改nacos中的配置文件，能影响到当前项目
@RefreshScope
@RequestMapping("/config")
public class ConfigTestByRefreshScope {

    @Value("${chen.gender}")
    private String gender;

    //从nacos配置中心上读取
    @GetMapping("/gender")
    public String getConfigString(){
        log.info("chen's gender is {}",gender);
        return gender;
    }
}

