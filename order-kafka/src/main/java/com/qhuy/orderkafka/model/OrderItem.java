package com.qhuy.orderkafka.model;




import com.qhuy.orderkafka.dto.ProductDTO;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderItem {
    @Id
    private String id;

    private ProductDTO productDTO;


    private Integer amount;

}
