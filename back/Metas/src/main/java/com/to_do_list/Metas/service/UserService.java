package com.to_do_list.Metas.service;

import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserDto;
import com.to_do_list.Metas.model.dto.UserLoginDTO;

public interface UserService {

    User saveUser(UserDto dto);
    Object loginUser(UserLoginDTO dto);
    User perfil();
    User findByIdUser(Integer id);
    void findByEmailUser(String email);
}
