package com.chen.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 * @create 2022-12-13
 * gateway定义全局过滤器 GlobalFilter
 */
//@Component
@Slf4j
public class CustomGlobalGatewayFilter implements GlobalFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //拿到请求中的参数auth,这里是uri路径的请求， ?auth=admin
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> requestParam = request.getQueryParams();
        String auth = requestParam.getFirst("auth");
        //判断请求中是否有auth=admin
        if ("admin".equals(auth)){
            log.info("带参请求 成功,被gateway global filter拦截");
            //放行
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        //如果没有，结束响应，并返回一个未认证的状态码
        return exchange.getResponse().setComplete();
    }
}
