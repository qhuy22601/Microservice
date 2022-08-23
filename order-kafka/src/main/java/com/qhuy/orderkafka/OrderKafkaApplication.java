package com.qhuy.orderkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableEurekaClient
@EnableKafka
@EnableConfigurationProperties
public class OrderKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderKafkaApplication.class,args);
    }
}
