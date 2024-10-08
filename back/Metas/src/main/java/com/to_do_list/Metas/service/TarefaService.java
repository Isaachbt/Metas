package com.to_do_list.Metas.service;

import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.dto.TarefaDto;

import java.util.List;

public interface TarefaService {
    List<TarefaDto> findAllTarefaUser(Integer id);
}
