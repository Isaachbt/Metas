package com.to_do_list.Metas.service.impl;

import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserDto;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDto saveUser(UserDto dto) {
        return null;
    }

    @Override
    public User findBYIdUser(Integer id) {
        return null;
    }
}
