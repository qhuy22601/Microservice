package com.qhuy.cart.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String name;
    private Double price;
}
