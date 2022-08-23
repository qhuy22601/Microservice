package com.qhuy.orderkafka.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Product {
    @Id
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
