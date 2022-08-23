package com.qhuy.product.webClientConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webConfig(){
        return WebClient.builder();
    }
}
