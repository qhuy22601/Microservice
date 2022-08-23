package com.qhuy.orderms.repository;

import com.qhuy.orderms.model.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderItemRepository extends MongoRepository<OrderItem, Long> {

    Optional<OrderItem> findById(String id);

    void deleteById(String orderItemId);
}
