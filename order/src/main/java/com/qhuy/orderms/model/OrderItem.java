package com.qhuy.orderms.model;


import com.qhuy.orderms.dto.ProductDTO;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderItem {
    @Id
    private String id;

    private String productId;

    private Integer amount;

}
