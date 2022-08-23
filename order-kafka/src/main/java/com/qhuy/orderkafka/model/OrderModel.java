package com.qhuy.orderkafka.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderModel {
    @Id
    private String id;
    private Long userId;

    private String orderItemId;
    @JsonIgnore
    private OrderItem orderItem;
    private Double totalBill;
}
