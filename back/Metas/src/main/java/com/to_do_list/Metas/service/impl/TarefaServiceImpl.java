package com.to_do_list.Metas.service.impl;


import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.service.TarefaService;
import com.to_do_list.Metas.service.UserService;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.TarefaNotFoundException;
import org.modelmapper.ModelMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepositorio repositorio;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;

    @Override
    public String saveTatefa(TarefaDto dto) {
        userService.findByIdUser(dto.getUserId());
        repositorio.save(mapper.map(dto,Tarefa.class));
        return "Salvo com sucesso.";
    }

    @Override
    public List<Tarefa> findAllTarefaUser(Integer id) {
      Optional<List<Tarefa>> tarefaList = repositorio.findByUserId(userService.findByIdUser(id).getId());
      tarefaList.orElseThrow(() -> new TarefaNotFoundException("Crie sua primeira tarefa."));
      return tarefaList.get();
    }

    @Override
    public void updateTarefa(TarefaDto dto) throws TarefaNotFoundException,NotFoundUserException{
        try {
                repositorio.findById(dto.getId()).orElseThrow(() -> new TarefaNotFoundException("Id tarefa invalido."));
                repositorio.findByUserId(dto.getUserId()).orElseThrow(() -> new NotFoundUserException("Id usuario não existe."));

                var tarefa = new Tarefa();
                BeanUtils.copyProperties(dto,tarefa);
                repositorio.save(tarefa);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
