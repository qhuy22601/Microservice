package com.qhuy.orderms.repository;

import com.qhuy.orderms.model.OrderModel;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveMongoRepository<OrderModel, Long> {

    OrderModel findOrderModelById(String id);
}
