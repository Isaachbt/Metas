package com.to_do_list.Metas.repositorio;

import com.to_do_list.Metas.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepositorio extends JpaRepository<Tarefa,Integer> {
}
