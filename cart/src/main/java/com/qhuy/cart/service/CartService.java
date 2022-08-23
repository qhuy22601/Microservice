package com.qhuy.cart.service;

import com.qhuy.cart.dto.CartDTO;
import com.qhuy.cart.dto.ItemDTO;
import com.qhuy.cart.dto.ProductDTO;
import com.qhuy.cart.dto.ResponseObject;
import com.qhuy.cart.model.CartModel;
import com.qhuy.cart.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    public ResponseObject getAll(){
        ResponseObject responseObject = new ResponseObject();

        responseObject.setStatus("success");
        responseObject.setMessage("success");
        responseObject.setPayload(cartRepo.findAll());
        return responseObject;
    }

    public ResponseObject save(CartModel cartModel){
        ResponseObject responseObject = new ResponseObject();
        responseObject.setMessage("success");
        responseObject.setStatus("success");
        responseObject.setPayload(cartRepo.save(cartModel));
        return responseObject;
    }


    //when user signup use synchronous webclient to create the cart at the same time
    public CartModel addItem(String name,Integer amount, long userId){
        ItemDTO item = new ItemDTO();
        ProductDTO product = new ProductDTO();
        product.setName(name);
        item.setProduct(product);
        item.setAmount(amount);
        List<ItemDTO> itemList = new ArrayList<>();
        itemList.add(item);
        Optional<CartModel> cart = cartRepo.findCartModelByUserId(userId);
        CartModel cartModel = cart.get();
        cartModel.setItems(itemList);
        return cartModel;
    }
}
