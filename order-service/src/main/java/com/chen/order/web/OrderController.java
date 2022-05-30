package com.chen.order.web;

import com.chen.order.pojo.Order;
import com.chen.order.pojo.User;
import com.chen.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;


@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

   @Resource
   private RestTemplate restTemplate;
    /**
     *这里将user和order拆分成了两个微服务，低耦合
     * 订单中有user_id，user暂时为空，需要调用查询user的接口，得到user，返回给order中的user
     * 如果不使用组件，则使用restTemplate进行调用；
     */
    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        return getOrderByRestTemplate(orderId);
    }

    //restTemplate调用http请求
    private Order getOrderByRestTemplate(Long orderId) {
        Order order = orderService.queryOrderById(orderId);
        /** 使用restTemplate调用远程服务时可能会遇到的问题：
         *  order-service在发起远程调用的时候，该如何得知user-service实例的ip地址和端口？
         *  有多个user-service实例地址，order-service调用时该如何选择？
         *  order-service如何得知某个user-service实例是否依然健康，是不是已经宕机？
         */
        User user = restTemplate.getForObject("http://localhost:8081/user/" + order.getUserId(), User.class);

        order.setUser(user);

        return order;
    }


}
