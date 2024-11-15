package com.to_do_list.Metas.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserLoginDTO;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.AuthenticationService;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.PasswordIncorreta;
import com.to_do_list.Metas.service.exception.UserExistException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticationServiceUserImp implements AuthenticationService {

    @Autowired
    private UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByEmail(username)
                .orElseThrow(() -> new NotFoundUserException("Usuário não encontrado: " + username));

        if (user == null) {
            throw new UserExistException("Credenciais inválidas para o usuário: " + username);
        }

        return user;
    }

    @Override
    public String login(UserLoginDTO dto) throws NotFoundUserException, PasswordIncorreta {
        return obterToken(dto);
    }

    @Override
    public String obterToken(UserLoginDTO dto) {
        var user = new User();
        BeanUtils.copyProperties(dto,user);
        return gerarTokenJwt(user);
    }

    @Override
    public String validToken(String token) {
        try{

            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.require(algorithm)
                    .withIssuer("metas")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            return "";
        }
    }


    @Override
    public String gerarTokenJwt(User user) {
        try{

            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.create()
                    .withIssuer("metas")
                    .withSubject(user.getEmail())
                    .withExpiresAt(gerarDateExpiracao())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao tentar gerar o token");
        }
    }

    private Instant gerarDateExpiracao(){
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String getLoginFromExpiredToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");
            return JWT.require(algorithm)
                    .withIssuer("gerenciar-biblioteca-spring")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (TokenExpiredException e) {
            return JWT.decode(token).getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido", e);
        }
    }
}
