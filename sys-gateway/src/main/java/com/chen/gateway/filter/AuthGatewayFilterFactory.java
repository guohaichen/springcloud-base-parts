package com.chen.gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @author cgh
 * @create 2022-12-14 10:34
 * //todo 照着码缘做一下 验证
 * https://java-family.cn/#/spring-cloud/gateway?id=spring-cloud-gateway%e5%87%a0%e4%b8%aa%e5%bf%85%e7%9f%a5%e7%9a%84%e6%9c%af%e8%af%ad%ef%bc%9f
 */
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {


    @Override
    public GatewayFilter apply(Config config) {
        return null;
    }
    @Data
    public static class Config{
        private boolean enabled;
    }
}
