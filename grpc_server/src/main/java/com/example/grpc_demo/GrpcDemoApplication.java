package com.example.grpc_demo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author cuijing
 */
@SpringBootApplication(exclude = { DruidDataSourceAutoConfigure.class})
@EnableTransactionManagement
public class GrpcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcDemoApplication.class, args);
    }

}
