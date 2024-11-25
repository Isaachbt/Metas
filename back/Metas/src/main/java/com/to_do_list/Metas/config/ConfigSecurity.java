package com.to_do_list.Metas.config;

import com.to_do_list.Metas.controller.exceptions.CustomAccessDeniedHandler;
import com.to_do_list.Metas.controller.exceptions.CustomAuthenticationEntryPoint;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private final CustomAccessDeniedHandler error403;
    @Autowired
    private CustomAuthenticationEntryPoint error401;

    public ConfigSecurity(CustomAccessDeniedHandler accessDeniedHandler) {
        this.error403 = accessDeniedHandler;
    }

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/perfil").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/tarefas/findAll").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/tarefas/save-tarefa").hasRole("USER")
                        .requestMatchers(HttpMethod.PUT, "/tarefa/update-tarefa").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/tarefa/delete").hasRole("USER")
                        .anyRequest().authenticated())
                .exceptionHandling(e ->e.accessDeniedHandler(error403)
                        .authenticationEntryPoint(error401))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                //.exceptionHandling(exceptions -> exceptions.accessDeniedHandler(error403))
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
