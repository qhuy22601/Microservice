package com.qhuy.orderms.controller;

import com.qhuy.orderms.dto.ProductDTO;
import com.qhuy.orderms.model.OrderItem;
import com.qhuy.orderms.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderitem")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/getall")
    public ResponseEntity<List<OrderItem>> getAll(){
        return new ResponseEntity<>(orderItemService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<OrderItem> add(@PathVariable("id") Long productId,@RequestParam("amount") Integer amount){
        return new ResponseEntity<>(orderItemService.save(productId, amount),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderItem> get(@PathVariable("id") String id){
        return new ResponseEntity<>(orderItemService.getById(id),HttpStatus.OK);
    }
}
