package com.chen.order;

import com.chen.api.feignClients.UserClients;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("com.chen.order.mapper")
@SpringBootApplication
/* 开启openfeign客户端,同时将api接口中的bean交由spring管理，否则会找不到这个bean，
因为这里application包扫描是com.chen.order 扫描不到feign-common-api中的UserClients（所在包为com.chen.api）
或者 @EnableFeignClients(basePackages = "com.chen.api.feignClients") 全量导入 ，下面是按需导入*/
@EnableFeignClients(clients = {UserClients.class})
public class OrderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApplication.class, args);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            //输出验证是否有UserClients
            System.out.println(beanDefinitionName);
        }
    }

    /*LoadBalanced实现负载均衡调用*/
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}