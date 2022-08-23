package com.qhuy.orchestrator.config;

import com.qhuy.orchestrator.service.OrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import test.qhuy.common.dto.OrchestratorRequestDto;
import test.qhuy.common.dto.OrchestratorResponseDto;

import java.util.function.Function;

@Configuration
public class Orchestrator {
    @Autowired
    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDto>, Flux<OrchestratorResponseDto>> processor(){
        return flux -> flux
                .flatMap(dto -> this.orchestratorService.orderProduct(dto))
                .doOnNext(dto -> System.out.println("Status : " + dto.getOrderStatus()));
    }

}
