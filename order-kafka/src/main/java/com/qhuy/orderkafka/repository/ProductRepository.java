package com.qhuy.orderkafka.repository;

import com.qhuy.orderkafka.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Long> {
}
