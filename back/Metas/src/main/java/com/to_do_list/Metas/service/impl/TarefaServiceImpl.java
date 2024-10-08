package com.to_do_list.Metas.service.impl;


import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.service.TarefaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepositorio repositorio;

    @Autowired
    private ModelMapper mapper;
    @Override
    public List<TarefaDto> findAllTarefaUser(Integer id) {

        BeanUtils.copyProperties(repositorio.findById(id).get(),TarefaDto.class);

        return Collections.singletonList(mapper.map(repositorio.findById(id), TarefaDto.class));
    }
}
