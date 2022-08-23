package com.qhuy.cart.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseObject {
    private String message;

    private String status;
    private Object payload;

}
