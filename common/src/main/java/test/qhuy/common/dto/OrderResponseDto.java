package test.qhuy.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.qhuy.common.event.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private String orderId;
    private Long userId;
    private Double amount;
    private Long productId;
    private OrderStatus status;
}
