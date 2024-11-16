package com.to_do_list.Metas.controller;

import com.to_do_list.Metas.controller.exceptions.StandarError;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserDto;
import com.to_do_list.Metas.model.dto.UserLoginDTO;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.UserService;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.PasswordIncorreta;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDTO dto){
        Optional<User> userSearch = repository.findByEmail(dto.getEmail());
        if (userSearch.isEmpty())
            throw new NotFoundUserException("Usuario não encotrado.");

        if (!passwordEncoder.matches(dto.getPassword(), userSearch.get().getPassword()))
            throw new PasswordIncorreta("Senha incorreta.");

        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword());
        authenticationManager.authenticate(authenticationToken);

        return ResponseEntity.ok(service.loginUser(dto));
    }
}
