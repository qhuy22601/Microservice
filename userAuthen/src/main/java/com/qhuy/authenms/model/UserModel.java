package com.qhuy.authenms.model;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private String email;

    private Double balance;

    private String role;

    private String ava;
}
