package com.chen.cloud.auth.controller;

import com.chen.cloud.auth.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cgh
 * @create 2023-08-08
 */
@RestController
@RequestMapping("auth")
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public Boolean userLogin(@RequestBody User user) {
        if (user != null) {
            log.info("username:{},password:{}", user.getUsername(), user.getPassword());
        }
        return user != null;
    }

}
