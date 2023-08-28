package com.chen.cloud.auth.filter;

import com.chen.cloud.auth.entity.User;
import com.chen.cloud.auth.service.UserDetailsServiceImpl;
import com.chen.cloud.auth.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cgh
 * @create 2023-08-23
 * 过滤器，拦截token，有token则校验
 */
@Slf4j
@Configuration
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isNotEmpty(token)) {
            log.info("token filters username : {} ", token);
//            UserDetails userDetails = userDetailsService.loadUserByUsername("root");
            //验证token成功
            String username;
            try {
                username = JwtUtils.validateJWT(token).getSubject();
            } catch (Exception e) {
                throw new RuntimeException("非法token" + e.getMessage());
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getName().equals(username)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            log.info("userDetails:{},{}", userDetails.getUsername(), userDetails.getPassword());
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
