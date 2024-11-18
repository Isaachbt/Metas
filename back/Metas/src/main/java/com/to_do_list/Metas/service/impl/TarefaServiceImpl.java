package com.to_do_list.Metas.service.impl;


import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.TarefaService;
import com.to_do_list.Metas.service.UserService;
import com.to_do_list.Metas.service.exception.IllegalArgument;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.TarefaNotFoundException;
import com.to_do_list.Metas.utils.AuthenticationFacade;
import org.modelmapper.ModelMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepositorio repositorio;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AuthenticationFacade facade;

    @Override
    public String saveTatefa(TarefaDto dto) {

        User user = validUser();
        dto.setUserId(user);

        userService.findByIdUser(dto.getUserId().getId());
        repositorio.save(mapper.map(dto,Tarefa.class));
        return "Salvo com sucesso.";
    }

    @Override
    public List<Tarefa> findAllTarefaUser(UUID id) {
      Optional<List<Tarefa>> tarefaList = repositorio.findByUserId(id);
      tarefaList.orElseThrow(() -> new TarefaNotFoundException("Crie sua primeira tarefa."));
      return tarefaList.get();
    }

    @Override
    public void updateTarefa(TarefaDto dto){

        User user = validUser();
                repositorio.findById(dto.getId()).orElseThrow(() -> new TarefaNotFoundException("ID tarefa não encotrado"));
                repositorio.findByUserId(user.getId()).orElseThrow(() -> new NotFoundUserException("ID associado a tarefa, não encontrado."));


        try {
                var tarefa = new Tarefa();
                tarefa.setUser(user);
                BeanUtils.copyProperties(dto,tarefa);
                repositorio.save(tarefa);

        } catch (Exception e) {
            throw new IllegalArgument("Não foi possivel atualizar a tarefa");
        }
    }

    @Override
    public void deleteTarefa(Integer idTarefa) {
        Optional<Tarefa> tarefaOptional = repositorio.findById(idTarefa);
        tarefaOptional.orElseThrow(() -> new TarefaNotFoundException("ID tarefa não encotrado"));

        if ((Objects.equals(tarefaOptional.get().getUser(), facade.getCurrentUser().getId()))){
            throw new NotFoundUserException("ID do usuario não esta associado a essa tarefa.");
        }

        try{
            repositorio.delete(tarefaOptional.get());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar deletar.");
        }
    }

    @Override
    public User validUser() {
        Optional<User> user = userRepository.findById(facade.getCurrentUser().getId());
        if (user.isEmpty())
            throw new NotFoundUserException("Usuario não encotrado.");
        return user.get();
    }
}
