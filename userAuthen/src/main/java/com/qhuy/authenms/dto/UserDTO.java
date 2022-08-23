package com.qhuy.authenms.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String message;
    private String status;
    private Object payload;
}
