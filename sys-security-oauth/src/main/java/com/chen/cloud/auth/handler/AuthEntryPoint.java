package com.chen.cloud.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cgh
 * @create 2023-08-24
 * 登录失败处理
 */
@Service
@Slf4j
public class AuthEntryPoint implements AuthenticationEntryPoint  {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        log.info("登录失败:{}",authException.getMessage());
        //todo 全局异常处理器 处理authException，做响应
        throw authException;
    }
}
