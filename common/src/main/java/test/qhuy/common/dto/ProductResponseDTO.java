package test.qhuy.common.dto;

import lombok.*;
import test.qhuy.common.event.ProductStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductResponseDTO {

    private Long orderId;
    private Long userId;
    private Long productId;
    private ProductStatus status;

}
