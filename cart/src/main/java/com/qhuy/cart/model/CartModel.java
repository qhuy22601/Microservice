package com.qhuy.cart.model;

import com.qhuy.cart.dto.ItemDTO;
import com.qhuy.cart.dto.ProductDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart")
public class CartModel {
    @Id
    private String id;


    private long userId;


    List<ItemDTO> items;

}
