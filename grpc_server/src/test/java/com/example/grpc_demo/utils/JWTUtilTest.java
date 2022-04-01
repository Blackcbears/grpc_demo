package com.example.grpc_demo.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-09 21:47
 */
class JWTUtilTest {

    Map<String, Integer> map = new HashMap<>();
    @Test
     void getMinKey() {
        if (map == null || map.isEmpty()){
            System.out.println("null");;
        }
        Integer minValue = Integer.MAX_VALUE;
        String minKey = null;
        Set<Map.Entry<String,Integer>> entrySet = map.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {

            //key value 代表每轮遍历出来的值
            String key   = entry.getKey();
            Integer value = entry.getValue();

            if(minValue > value ) {

                //flagKey flagValue 当判断出最大值是将最大值赋予该变量
                minKey   = key;
                minValue = value;
            }
        }

    }
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