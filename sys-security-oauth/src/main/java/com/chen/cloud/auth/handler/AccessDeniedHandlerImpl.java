package com.chen.cloud.auth.handler;

import com.chen.cloud.auth.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.bouncycastle.util.encoders.UTF8;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cgh
 * @create 2023-08-29
 * 授权异常处理器
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("授权异常:{}", accessDeniedException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("未授权！请联系管理员！");
    }
}
