package test.qhuy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.qhuy.common.event.PaymentStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long userId;
    private Long orderId;
    private Double amount;
    private PaymentStatus status;
}
