package com.qhuy.orderms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import test.qhuy.common.event.OrderStatus;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderModel {

    @Id
    private String id;
    private Long userId;


    private Long productId;

//    private Double price;

    private Double price;

    private OrderStatus orderStatus;

}
