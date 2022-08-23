package com.qhuy.orchestrator.service;


import com.qhuy.orchestrator.service.steps.PaymentStep;
import com.qhuy.orchestrator.service.steps.ProductStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import test.qhuy.common.dto.*;
import test.qhuy.common.event.OrderStatus;

import java.util.List;

@Service
public class OrchestratorService {

    @Autowired
    @Qualifier("payment")
    private WebClient paymentClient;

    @Autowired
    @Qualifier("product")
    private WebClient productClient;


    public Mono<OrchestratorResponseDto> orderProduct(OrchestratorRequestDto requestDto){
        WorkFlow orderWorkFlow = this.getOrderWorkFlow(requestDto);
        return Flux.fromStream(() -> orderWorkFlow.getSteps().stream())
                .flatMap(WorkFlowStep::process)
                .handle(((aBoolean, synchronousSink) ->{
                    if(aBoolean){
                        synchronousSink.next(true);
                    }else{
                        synchronousSink.error(new WorkFlowException("Create order failed"));
                    }
                }))
                .then(Mono.fromCallable(() -> getResponseDTO(requestDto, OrderStatus.ORDER_COMPLETED)))
        .onErrorResume(ex -> this.revertOrder(orderWorkFlow, requestDto));
    }
    private Mono<OrchestratorResponseDto> revertOrder(final WorkFlow workflow, final OrchestratorRequestDto requestDTO){
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkFlowStepStatus.COMPLETE))
                .flatMap(WorkFlowStep::revert)
                .retry(3)
                .then(Mono.just(this.getResponseDTO(requestDTO, OrderStatus.ORDER_CANCEL)));
    }

    private WorkFlow getOrderWorkFlow(OrchestratorRequestDto requestDto){
        WorkFlowStep paymentStep = new PaymentStep(this.paymentClient, this.getPaymentRequestDto(requestDto));
        WorkFlowStep productStep = new ProductStep(this.productClient, this.getProductRequestDto(requestDto));
        return new OrderWorkFlow(List.of(paymentStep, productStep));
    }

    private OrchestratorResponseDto getResponseDTO(OrchestratorRequestDto requestDTO, OrderStatus status){
        OrchestratorResponseDto responseDTO = new OrchestratorResponseDto();
        responseDTO.setOrderId(requestDTO.getOrderId());
        responseDTO.setTotalBill(requestDTO.getTotalBill());
        responseDTO.setProductId(requestDTO.getProductId());
        responseDTO.setUserId(requestDTO.getUserId());
        responseDTO.setOrderStatus(status);
        return responseDTO;
    }
    private PaymentRequestDTO getPaymentRequestDto(OrchestratorRequestDto requestDto){
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setUserId(requestDto.getUserId());
        paymentRequestDTO.setAmount(requestDto.getTotalBill());
        paymentRequestDTO.setOrderId(requestDto.getOrderId());
        return paymentRequestDTO;
    }

    private ProductRequestDTO getProductRequestDto(OrchestratorRequestDto requestDto){
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setUserId(requestDto.getUserId());
        productRequestDTO.setProductId(requestDto.getProductId());
        productRequestDTO.setOrderId(requestDto.getOrderId());
        return productRequestDTO;
    }
}
