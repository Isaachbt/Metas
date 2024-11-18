package com.to_do_list.Metas.service;

import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserDto;
import com.to_do_list.Metas.model.dto.UserLoginDTO;

import java.util.UUID;

public interface UserService {

    User saveUser(UserDto dto);
    Object loginUser(UserLoginDTO dto);
    User perfil();
    User findByIdUser(UUID id);
    void findByEmailUser(String email);
}
