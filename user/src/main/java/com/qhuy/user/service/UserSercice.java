package com.qhuy.user.service;

import com.qhuy.user.model.UserModel;
import com.qhuy.user.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.qhuy.common.dto.PaymentRequestDTO;
import test.qhuy.common.dto.PaymentResponseDto;
import test.qhuy.common.event.PaymentStatus;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserSercice {
    @Autowired
    private UserRepo userRepo;

    public List<UserModel> getAll(){
        return userRepo.findAll();
    }

    public UserModel getById(Long id){
        Optional<UserModel> userOpt = userRepo.findById(id);
        return userOpt.get();
    }

    public UserModel deductBalance(Long id, Double totalBill){
        Optional<UserModel> userOpt = userRepo.findById(id);
        UserModel userModel = userOpt.get();
        userModel.setBalance(userModel.getBalance()-totalBill);
        return userRepo.save(userModel);
    }

    public PaymentResponseDto debit(PaymentRequestDTO paymentRequestDTO){
        UserModel userModel = userRepo.findById(paymentRequestDTO.getUserId()).get();
        Double balance = userModel.getBalance();

        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setUserId(paymentResponseDto.getUserId());
        paymentResponseDto.setOrderId(paymentRequestDTO.getOrderId());
        paymentResponseDto.setAmount(paymentRequestDTO.getAmount());
        paymentResponseDto.setStatus(PaymentStatus.PAYMENT_REJECTED);
        if(balance >= paymentRequestDTO.getAmount()){
            paymentResponseDto.setStatus(PaymentStatus.PAYMENT_APPROVED);
            userModel.setBalance(userModel.getBalance()-paymentRequestDTO.getAmount());
            userRepo.save(userModel);
        }

        return  paymentResponseDto;
    }


}
