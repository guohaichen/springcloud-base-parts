package com.chen.cloud.auth.service;

import com.chen.cloud.auth.entity.SysUser;
import com.chen.cloud.auth.mapper.AuthUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cgh
 * @create 2023-08-24
 * 用户从数据库校验
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //返回用户信息和权限集合
        SysUser sysUser = authUserMapper.queryUser(username);
        log.info("loadUserByUsername,username :{}, password:{}", username, sysUser.getPassword());
        List<GrantedAuthority> authorizes = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
        return new User(username, sysUser.getPassword(), authorizes);
    }
}
