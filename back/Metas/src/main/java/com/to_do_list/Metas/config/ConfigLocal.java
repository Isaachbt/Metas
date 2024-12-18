package com.to_do_list.Metas.config;

import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.User;

import com.to_do_list.Metas.model.role.RoleUser;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.repositorio.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Profile("local")
public class ConfigLocal {

    @Autowired
    private TarefaRepositorio repositorio;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void saveTarefa(){
//        Tarefa t = new Tarefa(null,"Dormir", LocalDateTime.now(),LocalDateTime.now(),6,1);
//        Tarefa t2 = new Tarefa(null,"Comer", LocalDateTime.now(),LocalDateTime.now(),7,2);
//        User user = new User(null,"Isaac@gmail","123", RoleUser.USER);
//        User user2 = new User(null,"Net@gmail","123", RoleUser.USER);
//
//        userRepository.saveAll(List.of(user,user2));
        //repositorio.saveAll(List.of(t,t2));
    }
}
