package com.to_do_list.Metas.service;

import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserDto;

public interface UserService {

    UserDto saveUser(UserDto dto);
    User findBYIdUser(Integer id);
}
