package com.to_do_list.Metas.service;

import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.TarefaDto;

import java.util.List;
import java.util.UUID;

public interface TarefaService {
    String saveTatefa(TarefaDto dto);
    List<Tarefa> findAllTarefaUser(UUID id);
    void updateTarefa(TarefaDto dto);
    void deleteTarefa(Integer idTarefa);
    User validUser();
}
