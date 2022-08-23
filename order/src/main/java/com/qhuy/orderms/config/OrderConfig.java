package com.qhuy.orderms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import test.qhuy.common.dto.OrchestratorRequestDto;

@Configuration
public class OrderConfig {
    @Bean
    public Sinks.Many<OrchestratorRequestDto> sink(){
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<OrchestratorRequestDto> flux(Sinks.Many<OrchestratorRequestDto> sink){
        return sink.asFlux();
    }
}
