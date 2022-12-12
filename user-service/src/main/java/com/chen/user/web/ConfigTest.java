package com.chen.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cgh
 * @create 2022-12-12 17:27
 */
@RestController
@Slf4j
@RequestMapping("/config")
public class ConfigTest {

    @Value("${chen.gender}")
    private String gender;

    //从nacos配置中心上读取
    @GetMapping("/gender")
    public String getConfigString(){
        log.info("chen's gender is {}",gender);
        return gender;
    }


}

