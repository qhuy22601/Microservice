package com.qhuy.orchestrator.service;

import lombok.Data;
import reactor.core.publisher.Mono;


public interface WorkFlowStep {

    WorkFlowStepStatus getStatus();
    Mono<Boolean> process();
    Mono<Boolean> revert();

}
