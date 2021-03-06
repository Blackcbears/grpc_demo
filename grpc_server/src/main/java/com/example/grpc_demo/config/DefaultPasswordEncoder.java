package com.example.grpc_demo.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-09 22:16
 */
@Component
public class DefaultPasswordEncoder implements PasswordEncoder {

    /**
     * 转换请求传入的密码
     * @param charSequence -请求传入的密码
     * @return -加工(加密)后的密码
     */
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    /**
     * 如何判断用户密码相同
     * @param charSequence -请求传入的密码
     * @param s -数据库中的密码
     * @return -true 密码匹配 false 密码不匹配
     */
    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
