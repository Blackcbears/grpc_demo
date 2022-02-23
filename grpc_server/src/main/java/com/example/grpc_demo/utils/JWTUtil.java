package com.example.grpc_demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-09 21:03
 */
public class JWTUtil {

    private static final String ISSUER = "CuiJing";
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static String createToken(String userId) {
        Algorithm algorithm = getAlgorithm();
        //创建和签名令牌的Token构建器
        return JWT.create()
                //设置发布者
                .withIssuer(ISSUER)
                //添加自定义内容 (这里为添加userId)
                .withClaim("userId", userId)
                //生成时间
                .withIssuedAt(new Date())
                //到期时间
                //这里不设置到期,通过Redis来判断是否到期
                //.withExpiresAt(date)
                //指定算法进行签名
                .sign(algorithm);
    }
    /**
     * 通过密钥生成签名
     *
     * @return 签名对象
     */
    private static Algorithm getAlgorithm() {
        //使用HMAC256算法生成签名
        Algorithm algorithm;
        try {
            algorithm = Algorithm.HMAC256("CuiJing");
            return algorithm;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("错误");
        }
    }
}
