package com.to_do_list.Metas.controller;

import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.service.impl.TarefaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaServiceImpl service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<TarefaDto>> findAllTarefaUser(@PathVariable Integer id){

        return ResponseEntity.ok().body(service.findAllTarefaUser(id));

    }
}
