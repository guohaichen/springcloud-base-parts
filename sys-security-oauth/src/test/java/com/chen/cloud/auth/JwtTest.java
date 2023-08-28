package com.chen.cloud.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author cgh
 * @create 2023-08-28
 */
public class JwtTest {
    final String SECRET = "sdagk;g;a";

    @Test
    public void tokenCreate() {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("gender","0");
        userMap.put("telephone","158082139500");
        String token = JWT.create()
                .withClaim("userInfo",userMap)
                .withSubject("chen")
                .sign(algorithm);
        System.out.println(token);
    }

    //解码token
    @Test
    public void decodedToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ6zdWIiOiJjaGVuIn0.rA3zhAC6fyty6455WRVKFo-o7KqxFkOIanjts_5P3ZQ";
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            DecodedJWT verifier = JWT.require(algorithm)
                    // reusable verifier instance
                    .build()
                    .verify(token);
            System.out.println("true");
        } catch (Exception e) {
            System.out.println("false" + e.getMessage());
        }
    }

}
