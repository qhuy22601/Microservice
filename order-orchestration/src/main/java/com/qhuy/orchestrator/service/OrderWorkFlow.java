package com.qhuy.orchestrator.service;

import java.util.List;

public class OrderWorkFlow implements WorkFlow{

    private List<WorkFlowStep> steps;

    public OrderWorkFlow (List<WorkFlowStep> steps ){
        this.steps = steps;
    }

    @Override
    public List<WorkFlowStep> getSteps(){
        return this.steps;
    }
}
