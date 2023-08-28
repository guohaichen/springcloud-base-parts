package com.chen.cloud.auth.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.chen.cloud.auth.ResultResponse;
import com.chen.cloud.auth.entity.User;
import com.chen.cloud.auth.mapper.AuthUserService;
import com.chen.cloud.auth.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author cgh
 * @create 2023-08-08
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResultResponse<?> userLogin(@RequestBody User loginUser) {
        assert StringUtils.isNotBlank(loginUser.getUsername());
        //根据用户名 从数据库查user做校验
        User user = authUserService.queryUser(loginUser.getUsername());
        if (user != null && StringUtils.isNotBlank(user.getPassword()) && StringUtils.isNotBlank(loginUser.getPassword())) {
            if (user.getPassword().equals(loginUser.getPassword())) {
                String jwtToken = JwtUtils.createJWT(user.getUsername(), user.getPhone());
                // security authenticate
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
                authenticationManager.authenticate(token);
                return ResultResponse.OK("success", jwtToken);
            }
        }
        return ResultResponse.error("用户名或密码错误");
    }

    @GetMapping("/test/{name}")
    public ResultResponse<String> test(@PathVariable String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String result = "hello " + name + authentication.toString();
        log.info("authentication:{}", authentication);
        return ResultResponse.OK("success", result);
    }

    @GetMapping("/testLogin")
    public String testLogin() {
        return "xxx";
    }


    @Autowired
    AuthUserService authUserService;

    @GetMapping("/userList")
    public ResultResponse<User> getUserList() {
        return ResultResponse.OK("查询成功!", authUserService.queryUser("cgh"));
    }
}
