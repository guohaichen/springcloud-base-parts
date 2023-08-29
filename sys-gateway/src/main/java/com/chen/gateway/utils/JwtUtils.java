package com.chen.gateway.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cgh
 * @create 2023-08-29
 */
@Slf4j
public class JwtUtils {
    private static final String SECRET = "private_secret_key"; // 用于签名的密钥

    public static DecodedJWT validateJWT(String token) {
        try {
            //设置签名算法和密钥
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (Exception e) {
            log.info("token exception:{}", e.getMessage());
            throw new JWTVerificationException(e.getMessage());
        }
    }
}
