package com.qhuy.cart.controller;

import com.qhuy.cart.dto.CartDTO;
import com.qhuy.cart.dto.ResponseObject;
import com.qhuy.cart.model.CartModel;
import com.qhuy.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> getAll(){
        return new ResponseEntity<>(cartService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public  ResponseEntity<ResponseObject> save(@RequestBody  CartModel cartModel){
        return new ResponseEntity<>(cartService.save(cartModel),HttpStatus.OK);
    }

    @PostMapping("/additem/{amount}/{userId}")
    public ResponseEntity<CartModel> addItem(@PathVariable("amount") Integer amount,@RequestParam("name") String name,@PathVariable("userId") Long userId){
        return new ResponseEntity<>(cartService.addItem(name, amount, userId),HttpStatus.OK);
    }
}
