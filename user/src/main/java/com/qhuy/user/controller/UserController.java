package com.qhuy.user.controller;

import com.qhuy.user.model.UserModel;
import com.qhuy.user.service.UserSercice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.qhuy.common.dto.PaymentRequestDTO;
import test.qhuy.common.dto.PaymentResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserSercice userSercice;
    @GetMapping("/getall")
    public ResponseEntity<List<UserModel>> getAll(){
        return  new ResponseEntity<>(userSercice.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userSercice.getById(id), HttpStatus.OK);
    }

    @PutMapping("/deduct/{id}/{bill}")
    public ResponseEntity<UserModel> deductBalance(@PathVariable("id") Long id, @PathVariable("bill")Double totalBill){
        return new ResponseEntity<>(userSercice.deductBalance(id, totalBill),HttpStatus.OK);
    }

    @PostMapping("/debit")
    public ResponseEntity<PaymentResponseDto> debit(@RequestBody PaymentRequestDTO paymentRequestDTO){
        return new ResponseEntity<>(userSercice.debit(paymentRequestDTO),HttpStatus.OK);
    }
}
