package com.to_do_list.Metas.service.impl;


import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.TarefaService;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepositorio repositorio;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Override
    public List<TarefaDto> findAllTarefaUser(Integer id) {
        //findBYIdUser(id); preciso usar a fk para pesquisar por ela.
        return Collections.singletonList(mapper.map(repositorio.findById(id), TarefaDto.class));
    }

    @Override
    public void findBYIdUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(() -> new NotFoundUserException("Usuario não encontrado."));
    }
}
