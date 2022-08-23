package test.qhuy.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long userId;
    private Long productId;
    private String orderId;
}
