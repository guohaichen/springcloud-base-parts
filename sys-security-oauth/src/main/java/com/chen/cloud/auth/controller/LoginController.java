package com.chen.cloud.auth.controller;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.chen.cloud.auth.ResultResponse;
import com.chen.cloud.auth.entity.SysUser;
import com.chen.cloud.auth.mapper.AuthUserMapper;
import com.chen.cloud.auth.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public ResultResponse<?> userLogin(@RequestBody SysUser loginUser) {
        assert StringUtils.isNotBlank(loginUser.getUsername());
        //根据用户名 从数据库查user做校验
        SysUser sysUser = authUserMapper.queryUser(loginUser.getUsername());
        if (sysUser != null && StringUtils.isNotBlank(sysUser.getPassword()) && StringUtils.isNotBlank(sysUser.getPassword())) {
            if (sysUser.getPassword().equals(loginUser.getPassword())) {
                String jwtToken = JwtUtils.createJWT(sysUser.getUsername(), sysUser.getPhonenumber());
                List<GrantedAuthority> authorizes = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
                // security authenticate
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword(),authorizes);
                authenticationManager.authenticate(token);
                log.info("user:{}", sysUser);
                //登录用户存入redis
                String loginKey = "login:user:" + sysUser.getPhonenumber();
                log.info("{}", loginKey);
                redisTemplate.opsForValue().set(loginKey, sysUser);

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
    AuthUserMapper authUserMapper;

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('admin')")
    public ResultResponse<SysUser> getUserList() {
        return ResultResponse.OK("查询成功!", authUserMapper.queryUser("cgh"));
    }
}
