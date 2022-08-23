package com.qhuy.orderkafka.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    private Long id;

    private String userName;

    private String password;

    private String email;

    private Double balance;

    private String role;

    private String ava;
}
