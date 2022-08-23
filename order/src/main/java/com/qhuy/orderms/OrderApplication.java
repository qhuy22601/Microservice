package com.qhuy.orderms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableEurekaClient
//@EnableKafka
//@EnableConfigurationProperties
public class OrderApplication {
    @Bean
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }


}
