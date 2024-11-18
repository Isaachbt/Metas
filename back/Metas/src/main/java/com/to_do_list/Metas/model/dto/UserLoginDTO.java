package com.to_do_list.Metas.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
