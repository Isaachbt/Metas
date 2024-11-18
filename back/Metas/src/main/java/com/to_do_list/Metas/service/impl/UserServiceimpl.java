package com.to_do_list.Metas.service.impl;

import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserDto;
import com.to_do_list.Metas.model.dto.UserLoginDTO;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.AuthenticationService;
import com.to_do_list.Metas.service.UserService;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.UserExistException;
import com.to_do_list.Metas.utils.AuthenticationFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthenticationFacade facade;

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
    public Object loginUser(UserLoginDTO dto) throws NotFoundUserException{
        Optional<User> user = repository.findByEmail(dto.getEmail());
        if (user.isEmpty())
            throw new NotFoundUserException("Usuario não encontrado.");


        try{
            User userSave = user.get();
            userSave.setOnline(true);
            String token = authenticationService.login(dto);
            repository.save(userSave);
            return token;
        }catch (Exception e){
            throw new IllegalArgumentException("Não foi possivel efetuar login.");
        }
    }

    @Override
    public User perfil() {

            Optional<User> user = repository.findById(facade.getCurrentUser().getId());
            if (user.isEmpty())
                throw new NotFoundUserException("Usuario não encontrado.");
            try {
                return user.get();
            }catch (Exception e){
                throw new IllegalArgumentException("Erro ao tentar recuperar informações.");
            }

    }

    @Override
    public User findByIdUser(UUID id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new NotFoundUserException("Usuario não encontrado."));
    }

    @Override
    public void findByEmailUser(String email) {
        if (repository.existsByEmail(email)){
            throw new UserExistException("Email já cadastrado no sistema.");
        }
    }
}
