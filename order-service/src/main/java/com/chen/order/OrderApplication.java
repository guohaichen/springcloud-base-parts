package com.chen.order;

import com.chen.api.feignClients.UserClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("com.chen.order.mapper")
@SpringBootApplication
/* 开启openfeign客户端,同时将api接口中的bean交由spring管理，否则会找不到这个bean，
因为这里application包扫描式com.chen.order.mapper，扫描不到feign-common-api中的UserClients
或者 @EnableFeignClients(basePackages = "com.chen.api.feignClients") 全量导入 ，下面是俺需导入*/
@EnableFeignClients(clients = {UserClients.class})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /*LoadBalanced实现负载均衡调用*/
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}