package com.qhuy.product.repository;


import com.qhuy.product.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
