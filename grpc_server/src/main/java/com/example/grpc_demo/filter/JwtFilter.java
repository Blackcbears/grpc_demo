package com.example.grpc_demo.filter;


import com.example.grpc_demo.utils.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description JWT请求过滤器
 * @author znegyu
 * @date  2020-12-11 10:11
 **/
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    //private static final String JWT_BEARER = "Bearer ";
    private static final String JWT_HEADER = "Authorization";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //先获取请求头中的token
        String jwtToken = request.getHeader(JWT_HEADER);
        if (jwtToken != null && !"".equals(jwtToken.trim())) {
            //校验token的正确性并获取用户唯一标识符(这里的唯一标识符就是用户的身份证号码)
            String id = JWTUtil.getUserId(jwtToken);
            //登录用户没有认证时，先进行认证
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                //获取当前用户的信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(id);
                //授权
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
