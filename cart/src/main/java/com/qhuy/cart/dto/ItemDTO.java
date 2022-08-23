package com.qhuy.cart.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {

    private ProductDTO product;

    private Integer amount;

}
