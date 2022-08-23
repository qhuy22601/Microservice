package com.qhuy.orderms.controller;

import com.ctc.wstx.dtd.ModelNode;
import com.qhuy.orderms.model.OrderModel;
import com.qhuy.orderms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import test.qhuy.common.dto.OrderRequestDto;
import test.qhuy.common.dto.OrderResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getall")
    public ResponseEntity<Flux<OrderResponseDto>> getAll(){
        return new ResponseEntity<>(orderService.all(), HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<OrderModel> get(@PathVariable("id") String id){
        return new ResponseEntity<>(orderService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/createorder")
    public ResponseEntity<Mono<OrderModel>> createOrder(@RequestBody Mono<OrderRequestDto> mono){
        return new ResponseEntity<>(mono.flatMap(this.orderService::createOrder),HttpStatus.OK);
    }

//    @PostMapping("/orderplace/{userId}")
//    public ResponseEntity<OrderModel> orderPlace(@RequestParam("orderId") String orderItemId,@PathVariable("userId") Long userId){
//        return new ResponseEntity<>(orderService.orderPlace(orderItemId,userId), HttpStatus.OK);
//    }
}
