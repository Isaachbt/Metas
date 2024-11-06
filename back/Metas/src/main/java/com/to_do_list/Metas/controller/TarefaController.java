package com.to_do_list.Metas.controller;

import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.service.impl.TarefaServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaServiceImpl service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/findAll/{id}")
    public ResponseEntity<List<TarefaDto>> findAllTarefaUser(@PathVariable Integer id){
        return ResponseEntity.ok().body(
                service.findAllTarefaUser(id)
                        .stream()
                        .map(x -> mapper.map(x,TarefaDto.class)).toList());
    }

    @PostMapping("/save-tarefa")
    public ResponseEntity<String> saveTarefa(@RequestBody @Valid TarefaDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveTatefa(dto));
    }

    @PutMapping("/update-tarefa")
    public ResponseEntity<String> updateTarefa(@RequestBody @Valid TarefaDto dto){
        service.updateTarefa(dto);
        return ResponseEntity.ok("Atualizado");
    }


}
