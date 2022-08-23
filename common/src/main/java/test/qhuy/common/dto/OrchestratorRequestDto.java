package test.qhuy.common.dto;

import lombok.Data;

@Data
public class OrchestratorRequestDto {
    private String orderId;

    private Long userId;

    private Long productId;

    private Double totalBill;
}
