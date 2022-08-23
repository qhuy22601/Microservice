package com.qhuy.orderkafka.repository;

import com.qhuy.orderkafka.model.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderItemRepository extends MongoRepository<OrderItem, String> {
}
