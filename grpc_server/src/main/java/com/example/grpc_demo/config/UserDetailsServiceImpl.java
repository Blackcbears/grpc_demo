package com.example.grpc_demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-09 22:11
 */
@Slf4j
@Component("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching user: {}", username);
        return switch (username) {
            case "guest" -> new User(username, passwordEncoder.encode(username + "Password"), Collections.emptyList());
            case "user" -> new User(username, passwordEncoder.encode(username + "Password"), List.of(new SimpleGrantedAuthority("ROLE_GREET")));
            default -> throw new UsernameNotFoundException("Could not find user!");
        };
    }
}
