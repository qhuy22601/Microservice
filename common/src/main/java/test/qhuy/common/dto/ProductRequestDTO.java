package test.qhuy.common.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductRequestDTO {

    private Long userId;
    private Long productId;
    private String orderId;

}
