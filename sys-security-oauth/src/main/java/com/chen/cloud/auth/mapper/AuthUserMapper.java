package com.chen.cloud.auth.mapper;

import com.chen.cloud.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author cgh
 * @create 2023-08-28
 */
@Mapper
public interface AuthUserMapper {
    @Select("SELECT id,username,password,phonenumber FROM auth_user WHERE username = #{username}")
    User queryUser(@Param("username")String username);
}
