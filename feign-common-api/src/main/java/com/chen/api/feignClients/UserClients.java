package com.chen.api.feignClients;

import com.chen.api.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cgh
 * @create 2022-05-31 21:59
 * openfeign客户端
 */
//user-service对应着生产者在注册中心的服务名
@FeignClient("user-service")
public interface UserClients {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id")Long id);
}
