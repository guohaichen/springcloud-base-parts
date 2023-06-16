package com.chen.order.web;

import com.chen.api.feignClients.UserClients;
import com.chen.api.pojo.User;
import com.chen.order.pojo.Order;
import com.chen.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RestController
@Slf4j
@RefreshScope
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private UserClients userClients;

    /**
     * 这里将user和order拆分成了两个微服务，低耦合
     * 订单中有user_id，user暂时为空，需要调用查询user的接口，得到user，返回给order中的user
     * 如果不使用组件，则使用restTemplate进行调用；
     */
    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId,@RequestHeader(value = "Token",required = false) String token) {
        // 根据id查询订单并返回
//        return getOrderByRestTemplate(orderId);
        //通过openFeign调用远程服务
        log.info("get token from Gateway , is {}",token);
        return getOrderByOpenFeign(orderId);
    }



    private Order getOrderByOpenFeign(Long orderId){
        Order order = orderService.queryOrderById(orderId);
        //获取用户id，给接口远程调用，得到User
        Long userId = order.getUserId();

        // openFeign方法调用，重点！！！！！！！！！！！！！！！！！！！！！
        User user = userClients.findById(userId);

        order.setUser(user);
        return order;

    }

    //restTemplate调用http请求
    private Order getOrderByRestTemplate(Long orderId) {
        Order order = orderService.queryOrderById(orderId);
        /** 使用restTemplate调用远程服务时可能会遇到的问题：
         *  order-service在发起远程调用的时候，该如何得知user-service实例的ip地址和端口？
         *  有多个user-service实例地址，order-service调用时该如何选择？
         *  order-service如何得知某个user-service实例是否依然健康，是不是已经宕机？
         */
        //User user = restTemplate.getForObject("http://localhost:8081/user/" + order.getUserId(), User.class);
        //调用已在nacos注册的服务，
        User user = restTemplate.getForObject("http://user-service/user/" + order.getUserId(), User.class);
        order.setUser(user);

        return order;
    }


    @Value("${config.name}")
    private String configName;

    @GetMapping("/name")
    public String getNameFromNacosConfig(){
        log.info("从nacos配置中心读取到配置值为:{}",configName);
        return configName;
    }
}
