package com.to_do_list.Metas.service;

import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.TarefaDto;

import java.util.List;

public interface TarefaService {
    List<Tarefa> findAllTarefaUser(Integer id);
    void findBYIdUser(Integer id);
}
