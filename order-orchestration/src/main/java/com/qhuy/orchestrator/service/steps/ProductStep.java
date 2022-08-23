package com.qhuy.orchestrator.service.steps;

import com.qhuy.orchestrator.service.OrchestratorService;
import com.qhuy.orchestrator.service.WorkFlowStep;
import com.qhuy.orchestrator.service.WorkFlowStepStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import test.qhuy.common.dto.OrchestratorRequestDto;
import test.qhuy.common.dto.OrchestratorResponseDto;
import test.qhuy.common.dto.ProductRequestDTO;
import test.qhuy.common.dto.ProductResponseDTO;
import test.qhuy.common.event.ProductStatus;

import java.util.function.Function;

@Configuration
public class ProductStep implements WorkFlowStep {

    private final WebClient webClient;

    private final ProductRequestDTO requestDTO;

    private WorkFlowStepStatus stepStatus = WorkFlowStepStatus.PENDING;

    public ProductStep (WebClient webClient, ProductRequestDTO requestDTO){
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }


    @Override
    public WorkFlowStepStatus getStatus() {
        return this.stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return this.webClient
                .post()
                .uri("/api/product/deduct")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(ProductResponseDTO.class)
                .map(r->r.getStatus().equals(ProductStatus.AVAILABLE))
                .doOnNext(b->this.stepStatus=b? WorkFlowStepStatus.COMPLETE: WorkFlowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                .post()
                .uri("/api/product/save")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r->true)
                .onErrorReturn(false);
    }
}
