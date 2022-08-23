package com.qhuy.cart.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CartDTO {
    public String cartId;
    public long userId;
}
