package com.chen.cloud.auth.controller;

import com.chen.cloud.auth.ResultResponse;
import com.chen.cloud.auth.entity.User;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

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
    public ResultResponse<?> userLogin(@RequestBody User user) {
        //这里处理登录逻辑，保存username和password，并封装token，
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        log.info("token from mapping :{}", token);
        authenticationManager.authenticate(token);
        String tokenString = "tolen_sss_xxx";
        return ResultResponse.OK("success", tokenString);
    }

    @GetMapping("/test/{name}")
    public ResultResponse<String> test(@PathVariable String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String result = "hello " + name + authentication.toString();
        log.info("authentication:{}",authentication);
        return ResultResponse.OK("success", result);
    }

    @GetMapping("/testLogin")
    public String testLogin() {
        return "xxx";
    }

}
