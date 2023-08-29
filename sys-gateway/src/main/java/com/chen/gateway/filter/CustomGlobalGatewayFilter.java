package com.chen.gateway.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.chen.gateway.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
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
@Component
@Slf4j
public class CustomGlobalGatewayFilter implements GlobalFilter {
    public final static String TOKEN = "token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //拿到请求中的参数auth,这里是uri路径的请求， ?auth=admin
        ServerHttpRequest request = exchange.getRequest();
        String headerToken = request.getHeaders().getFirst(TOKEN);
        log.info("headerToken:{}",TOKEN);
        //对token进行校验
        if (headerToken != null) {
            try {
                JwtUtils.validateJWT(headerToken);
                log.info("gateway验证token成功，放行！");
                return chain.filter(exchange);
            } catch (Exception e) {
                log.info("token被伪造，token exception:{}", e.getMessage());
                throw new JWTVerificationException(e.getMessage());
            }
        }
        //无token，结束响应，并返回一个未认证的状态码
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
