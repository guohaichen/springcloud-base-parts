package com.chen.user.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cgh
 * @create 2023-09-27
 * OAuth2.0测试接口，返回一个字符串
 */
@RestController
@RequestMapping("user")
public class OAuthTestController {

    @GetMapping("/auth-test")
    public String authTest(){
        return "点赞成功";
    }

}
