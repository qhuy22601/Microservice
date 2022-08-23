package com.qhuy.orderkafka.repository;

import com.qhuy.orderkafka.model.OrderModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderModel, String> {
}
