package com.qhuy.product.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long brandId;

    private String image;

    private  Double price;

    private Integer quantity;

    private Integer shoesStateId;

    private Integer colorId;

    private Boolean delFlag;

}
