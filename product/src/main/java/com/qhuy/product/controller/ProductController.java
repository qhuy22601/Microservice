package com.qhuy.product.controller;

import com.qhuy.product.model.ProductModel;
import com.qhuy.product.serivce.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.qhuy.common.dto.ProductRequestDTO;
import test.qhuy.common.dto.ProductResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getall")
    public ResponseEntity<List<ProductModel>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductModel> get(@PathVariable("id") Long id){
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ProductModel> save(@RequestBody ProductModel productModel){
        return new ResponseEntity<>(productService.save(productModel),HttpStatus.OK);
    }

    @PutMapping("/reduce/{orderId}")
    public ResponseEntity<ProductModel> reduce(@PathVariable("orderId") String orderId){
        return new ResponseEntity<>(productService.reduceQuantity(orderId), HttpStatus.OK);
    }

    @PostMapping("/deduct")
    public ResponseEntity<ProductResponseDTO> deduct(@RequestBody ProductRequestDTO productRequestDTO){
        return new ResponseEntity<>(productService.deduct(productRequestDTO), HttpStatus.OK);
    }



}
