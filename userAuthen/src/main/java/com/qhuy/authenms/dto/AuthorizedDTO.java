package com.qhuy.authenms.dto;

import lombok.*;
import com.qhuy.authenms.model.UserModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorizedDTO {
    private UserModel userModel;

    private String token;
}
