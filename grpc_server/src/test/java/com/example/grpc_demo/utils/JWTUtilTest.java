package com.example.grpc_demo.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-09 21:47
 */
class JWTUtilTest {
    private static final String ID = "user";
    @Test
    void getUserId() {
    }

    @Test
    void createToken() {

        String token = JWTUtil.createToken(ID);
        String userId = JWTUtil.getUserId(token);
        Assertions.assertEquals(ID, userId);
    }
}