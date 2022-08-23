package com.qhuy.authenms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserLoginDTO {
    private String email;
    private String password;
}
