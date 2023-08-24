package com.chen.cloud.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author cgh
 * @create 2023-08-24
 * 用户从数据库校验
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //返回用户信息和权限集合

        log.info("userDetailsService 's username :{}",username);
        return new User(username,"cgh123456",new ArrayList<>());
    }
}
