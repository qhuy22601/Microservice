package com.qhuy.orderkafka.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDTO {

    private Long id;

    private String name;
    private Double price;

}
