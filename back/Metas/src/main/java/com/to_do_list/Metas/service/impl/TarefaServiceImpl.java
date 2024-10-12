package com.to_do_list.Metas.service.impl;


import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.service.TarefaService;
import com.to_do_list.Metas.service.UserService;
import com.to_do_list.Metas.service.exception.TarefaNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Tarefa> findAllTarefaUser(Integer id) {
      Optional<List<Tarefa>> tarefaList = repositorio.findByUserId(userService.findByIdUser(id).getId());
      tarefaList.orElseThrow(() -> new TarefaNotFoundException("Crie sua primeira tarefa."));

      return tarefaList.get();

    }

    @Override
    public void findBYIdUser(Integer id) {

    }
}
