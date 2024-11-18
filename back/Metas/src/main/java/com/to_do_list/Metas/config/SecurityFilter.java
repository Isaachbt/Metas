package com.to_do_list.Metas.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.to_do_list.Metas.controller.exceptions.StandarError;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extrairTokenHeader(request);

        if (token != null) {
            try {
                // Valida o token e extrai o email
                String email = authenticationService.validToken(token);

                // Busca o usuário pelo email
                repository.findByEmail(email).ifPresent(user -> {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });

            } catch (TokenExpiredException e) {
                // Responde com erro de token expirado
                handleTokenExpiration(response, token);
                return;
            } catch (Exception e) {
                // Responde com erro genérico se algo der errado
                handleAuthenticationError(response, e.getMessage());
                return;
            }
        }

        // Prossegue com a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private String extrairTokenHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Remove o prefixo "Bearer "
        }
        return null;
    }

    private void handleTokenExpiration(HttpServletResponse response, String token) throws IOException {
        String login = authenticationService.getLoginFromExpiredToken(token);
        repository.findByEmail(login).ifPresent(user -> {
            // Atualize o estado do usuário se necessário
            // user.setOnline(false);
            repository.save(user);
        });
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Token expirado. Por favor, faça login novamente.\"}");
    }

    private void handleAuthenticationError(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
    }
//
//    @Autowired
//    private AuthenticationService authenticationService;
//
//    @Autowired
//    private UserRepository repository;
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = extrairTokenHeader(request);
//
//        if (token != null) {
//            try {
//                String email = authenticationService.validToken(token);
//                Optional<User> user = repository.findByEmail(email);
//
//                if (user.isPresent()) {
//                    var authentication = new UsernamePasswordAuthenticationToken(user.get(), null, user.get().getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            } catch (TokenExpiredException e) {
//                handleTokenExpiration(token);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//
//    private String extrairTokenHeader(HttpServletRequest request) {
//        var authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null) {
//            return null;
//        }
//
//        if (!authHeader.split(" ")[0].equals("Bearer")) {
//            return null;
//        }
//        return authHeader.split(" ")[1];
//    }
//
//    private void handleTokenExpiration(String token) {
//        String login = authenticationService.getLoginFromExpiredToken(token);
//        Optional<User> user = repository.findByEmail(login);
//        user.ifPresent(u -> {
//            //u.setOnline(false);
//            repository.save(u);
//        });
//    }
}
