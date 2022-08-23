package com.qhuy.product.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDTo {

    private Long id;

    private String name;
    private Double price;
}
