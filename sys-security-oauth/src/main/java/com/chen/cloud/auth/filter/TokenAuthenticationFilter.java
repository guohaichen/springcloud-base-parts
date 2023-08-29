package com.chen.cloud.auth.filter;

import com.chen.cloud.auth.entity.SysUser;
import com.chen.cloud.auth.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author cgh
 * @create 2023-08-23
 * 过滤器，拦截token，有token则校验
 */
@Slf4j
@Configuration
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isNotEmpty(token)) {
            try {
                //无异常 验证token成功
                String phone = JwtUtils.validateJWT(token).getClaim("phone").asString();
                String userKey = "login:user:" + phone;
                //redis取出user
                SysUser sysUser = (SysUser) redisTemplate.opsForValue().get(userKey);
                log.info("user from redis :{}", sysUser);
                List<GrantedAuthority> authorizes = AuthorityUtils.commaSeparatedStringToAuthorityList("admiin");
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser, null, authorizes);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                throw new RuntimeException("非法token" + e.getMessage());
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
