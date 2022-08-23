package com.qhuy.authenms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableEurekaClient

public class AuthenMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenMSApplication.class, args);
    }
}
