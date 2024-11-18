package com.to_do_list.Metas.service;

import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserLoginDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {

    String login(UserLoginDTO dto);
    String obterToken(UserLoginDTO dto);

    String validToken(String token);
    String gerarTokenJwt(User user);
    String getLoginFromExpiredToken(String token);
}
