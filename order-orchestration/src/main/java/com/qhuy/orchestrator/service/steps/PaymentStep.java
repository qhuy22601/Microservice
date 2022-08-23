package com.qhuy.orchestrator.service.steps;

import com.qhuy.orchestrator.service.WorkFlow;
import com.qhuy.orchestrator.service.WorkFlowStep;
import com.qhuy.orchestrator.service.WorkFlowStepStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import test.qhuy.common.dto.PaymentRequestDTO;
import test.qhuy.common.dto.PaymentResponseDto;
import test.qhuy.common.event.PaymentStatus;

public class PaymentStep implements WorkFlowStep {
    private final WebClient webClient;
    private final PaymentRequestDTO requestDTO;
    private WorkFlowStepStatus stepStatus = WorkFlowStepStatus.PENDING;

    public PaymentStep(WebClient webClient, PaymentRequestDTO requestDTO) {
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
                .uri("/api/user/debit")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(PaymentResponseDto.class)
                .map(r -> r.getStatus().equals(PaymentStatus.PAYMENT_APPROVED))
                .doOnNext(b -> this.stepStatus = b ? WorkFlowStepStatus.COMPLETE : WorkFlowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return this.webClient
                .post()
                .uri("/api/user/save")
                .body(BodyInserters.fromValue(this.requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorReturn(false);
    }

}
