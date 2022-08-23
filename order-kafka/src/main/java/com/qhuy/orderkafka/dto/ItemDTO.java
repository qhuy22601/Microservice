package com.qhuy.orderkafka.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDTO {
    private Long id;

    private String name;

    private Long brandId;

    private String image;

    private  Double price;

    private Integer quantity;

    private Integer shoesStateId;

    private Integer colorId;

    private Boolean delFlag;
}
