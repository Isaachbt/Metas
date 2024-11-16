package com.to_do_list.Metas.controller;

import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.service.impl.TarefaServiceImpl;
import com.to_do_list.Metas.utils.AuthenticationFacade;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaServiceImpl service;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AuthenticationFacade facade;

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<TarefaDto>> findAllTarefaUser(){
        return ResponseEntity.ok().body(
                service.findAllTarefaUser(facade.getCurrentUser().getId())
                        .stream()
                        .map(x -> mapper.map(x,TarefaDto.class)).toList());
    }

    @PostMapping("/save-tarefa")
    @Transactional
    public ResponseEntity<String> saveTarefa(@RequestBody @Valid TarefaDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTatefa(dto));
    }

    @PutMapping("/update-tarefa")
    public ResponseEntity<String> updateTarefa(@RequestBody @Valid TarefaDto dto){
        service.updateTarefa(dto);
        return ResponseEntity.ok("Atualizado");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Objects> deleteTarefa(@PathVariable Integer idTarefa){
        service.deleteTarefa(idTarefa);
        return ResponseEntity.ok().build();

    }
}
