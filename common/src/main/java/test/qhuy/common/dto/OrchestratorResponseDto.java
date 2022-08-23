package test.qhuy.common.dto;

import lombok.Data;
import test.qhuy.common.event.OrderStatus;

@Data
public class OrchestratorResponseDto {
    private String orderId;

    private Long userId;

    private Long productId;

    private Double totalBill;

    private OrderStatus orderStatus;
}
