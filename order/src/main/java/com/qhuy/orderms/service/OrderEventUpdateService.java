package com.qhuy.orderms.service;

import com.qhuy.orderms.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import test.qhuy.common.dto.OrchestratorResponseDto;

@Service
public class OrderEventUpdateService {

    @Autowired
    private OrderRepository repository;

    public Mono<Void> updateOrder(final OrchestratorResponseDto responseDTO){
        return this.repository.findById(Long.valueOf(responseDTO.getOrderId()))
                .doOnNext(p -> p.setOrderStatus(responseDTO.getOrderStatus()))
                .flatMap(this.repository::save)
                .then();
    }
}

