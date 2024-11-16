package com.to_do_list.Metas.service.impl;

import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserDto;
import com.to_do_list.Metas.model.dto.UserLoginDTO;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.AuthenticationService;
import com.to_do_list.Metas.service.UserService;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.UserExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public User saveUser(UserDto dto) {
        findByEmailUser(dto.getEmail());
        try {
            return repository.save(mapper.map(dto,User.class));
        }catch (Exception e){
            throw new IllegalArgumentException("Erro ao tentar salvar usuario");
        }

    }

    @Override
    public Object loginUser(UserLoginDTO dto) {
        try{
            return authenticationService.login(dto);
        }catch (Exception e){
            throw new IllegalArgumentException("Não foi possivel efetuar login.");
        }
    }

    @Override
    public User findByIdUser(Integer id) throws NotFoundUserException{
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new NotFoundUserException("Usuario não encontrado."));
    }

    @Override
    public void findByEmailUser(String email)throws UserExistException {
        if (repository.existsByEmail(email)){
            throw new UserExistException("Email já cadastrado no sistema.");
        }
    }
}
