package com.qhuy.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderDTO {
    private String id;
    private Long userId;

    private String orderItemId;

    @JsonIgnore
    OrderItemDTO orderItems;
    private Double totalBill;

}
