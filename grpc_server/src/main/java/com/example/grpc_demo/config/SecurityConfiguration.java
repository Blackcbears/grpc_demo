package com.example.grpc_demo.config;

import com.example.grpc_demo.filter.ExceptionHandlerFilter;
import com.example.grpc_demo.filter.JwtFilter;
import com.example.grpc_demo.utils.JWTUtil;
import com.example.grpc_lib.SimpleGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.CompositeGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.check.AccessPredicate;
import net.devh.boot.grpc.server.security.check.AccessPredicateVoter;
import net.devh.boot.grpc.server.security.check.GrpcSecurityMetadataSource;
import net.devh.boot.grpc.server.security.check.ManualGrpcSecurityMetadataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-09 00:06
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(
            final UserDetailsService userDetailsService,
            final PasswordEncoder passwordEncoder) {

        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(final DaoAuthenticationProvider daoAuthenticationProvider) {
        final List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider);
        return new ProviderManager(providers);
    }


    @Bean
    GrpcAuthenticationReader authenticationReader() {
        final List<GrpcAuthenticationReader> readers = new ArrayList<>();
        // The actual token class is dependent on your spring-security library (OAuth2/JWT/...)
        readers.add(new BearerAuthenticationReader(accessToken -> {
            //获取当前用户的信息
            String id = JWTUtil.getUserId(accessToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(id);
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        }));
        return new CompositeGrpcAuthenticationReader(readers);
    }
    @Bean
    GrpcSecurityMetadataSource grpcSecurityMetadataSource() {
        final ManualGrpcSecurityMetadataSource source = new ManualGrpcSecurityMetadataSource();
        source.set(SimpleGrpc.getSayHelloMethod(), AccessPredicate.hasRole("ROLE_GREET"));
        source.setDefault(AccessPredicate.denyAll());
        return source;
    }

    @Bean
    AccessDecisionManager accessDecisionManager() {
        final List<AccessDecisionVoter<?>> voters = new ArrayList<>();
        voters.add(new AccessPredicateVoter());
        return new UnanimousBased(voters);
    }
}
