package com.example.grpc_demo.filter;

import com.example.grpc_demo.utils.ServletUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * 对象功能 - Filter异常铺抓
 * 开发人员：曾煜
 * 创建时间：2020/12/11 23:09
 * </pre>
 *
 * @author zengyu
 */
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            ServletUtils.render(request, response, e.getMessage());
        }
    }
}
