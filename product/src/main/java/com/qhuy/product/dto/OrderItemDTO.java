package com.qhuy.product.dto;

import com.qhuy.product.model.ProductModel;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemDTO {
    private String id;

    private ProductDTo productDTO;

    private Integer amount;

}
