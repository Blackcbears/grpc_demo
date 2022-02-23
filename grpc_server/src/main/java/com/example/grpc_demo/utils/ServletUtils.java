package com.example.grpc_demo.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-09 21:58
 */
public class ServletUtils {
    /**
     * 渲染到客户端
     *
     * @param object 待渲染的实体类，会自动转为json
     */
    public static void render(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Authorization,"
                + " Content-Type, Accept, Connection, User-Agent, Cookie");
        response.setHeader("Cache-Control", "no-cache");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.getWriter().print(JSON.toJSONString(object));
    }
}
