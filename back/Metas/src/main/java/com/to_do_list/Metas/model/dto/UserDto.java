package com.to_do_list.Metas.model.dto;

import com.to_do_list.Metas.model.role.RoleUser;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private UUID id;
    private String email;
    private String password;
    private RoleUser role;
}
