package com.qhuy.orderms.evenhandler;


import com.netflix.discovery.converters.Auto;
import com.qhuy.orderms.service.OrderEventUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import test.qhuy.common.dto.OrchestratorRequestDto;
import test.qhuy.common.dto.OrchestratorResponseDto;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class OrderEventHandler {

    @Autowired
    private Flux<OrchestratorRequestDto> flux;

    @Autowired
    private OrderEventUpdateService service;

    @Bean
    public Supplier<Flux<OrchestratorRequestDto>>  supplier (){
        return() ->flux;
    }

    @Bean
    public Consumer<Flux<OrchestratorResponseDto>> consumer(){
        return f -> f
                .doOnNext(c -> System.out.println("Comsuming:"+ c))
                .flatMap(responseDto -> this.service.updateOrder(responseDto))
                .subscribe();
    }

}
