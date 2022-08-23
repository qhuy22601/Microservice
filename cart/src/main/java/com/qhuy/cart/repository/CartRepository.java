package com.qhuy.cart.repository;

import com.qhuy.cart.model.CartModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<CartModel, String> {

    Optional<CartModel> findCartModelByUserId(long s);
}
