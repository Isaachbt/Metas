package com.to_do_list.Metas.service.impl;

import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.model.role.RoleUser;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.exception.IllegalArgument;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.TarefaNotFoundException;
import com.to_do_list.Metas.utils.AuthenticationFacade;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class TarefaServiceImplTest {

    @InjectMocks
    private TarefaServiceImpl tarefaService;
    @Mock
    private UserServiceimpl userService;
    @Mock
    private TarefaRepositorio tarefaRepositorio;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper mapper;
    private Tarefa tarefa;
    private TarefaDto tarefaDto;
    private User user;
    @Mock
    private AuthenticationFacade facade;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    @DisplayName("Retornando uma lista de tarefas.")
    void whenfindAllTarefaUserThenReturnAnListTheAnTarefaInstance() {
        inject();
        List<Tarefa> list = tarefaService.findAllTarefaUser(user.getId());
        assertNotNull(list);
        assertEquals(Tarefa.class,list.getFirst().getClass());
        assertEquals(list.getFirst().getUser().getId(),user.getId());
    }
    @Test
    void whenFindByAllTarefaUserThenReturnAnNotFoundUserException(){
        when(userService.findByIdUser(any(UUID.class))).thenThrow(new NotFoundUserException("Usuario não encontrado."));

             NotFoundUserException exception = assertThrows(NotFoundUserException.class, () ->
                tarefaService.findAllTarefaUser(userService.findByIdUser(user.getId()).getId()));

             assertEquals("Usuario não encontrado.",exception.getMessage());

    }
    @Test
    void whenFindByAllTarefaUserThenReturnAnTarefaNotFoundException(){
        when(userService.findByIdUser(any(UUID.class))).thenReturn(user);
        when(tarefaRepositorio.findByUserId(user.getId())).thenReturn(Optional.of(List.of(tarefa)));
        when(tarefaService.findAllTarefaUser(user.getId())).thenThrow(new TarefaNotFoundException("Crie sua primeira tarefa."));

        try{
            tarefaService.findAllTarefaUser(user.getId());
        }catch (Exception e){
            assertEquals(TarefaNotFoundException.class,e.getClass());
            assertEquals("Crie sua primeira tarefa.",e.getMessage());
        }
    }

    @Test
    @DisplayName("SalvandoTarefas")
    void saveTarefa(){
        when(facade.getCurrentUser()).thenReturn(user);
         inject();

         assertEquals(user.getId(),userService.findByIdUser(user.getId()).getId());
         TarefaDto tarefaDto = new TarefaDto();
         BeanUtils.copyProperties(tarefa,tarefaDto);
         assertEquals("Salvo com sucesso.",tarefaService.saveTatefa(tarefaDto));

    }

    @Test
    void whenSaveTarefaThenReturnUserNotFound(){
        when(facade.getCurrentUser()).thenReturn(user);
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(userService.findByIdUser(any(UUID.class))).thenThrow(new NotFoundUserException("Usuario não encontrado."));

            TarefaDto dto = new TarefaDto();
            dto.setUserId(user);

            NotFoundUserException erro = assertThrows(NotFoundUserException.class, () ->
                    tarefaService.saveTatefa(dto));

            assertEquals("Usuario não encontrado.",erro.getMessage());

    }
    @Test
    void whenUpdateThenReturnSucesso(){
        when(facade.getCurrentUser()).thenReturn(user);
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(tarefaRepositorio.findById(anyInt())).thenReturn(Optional.of(tarefa));
        when(tarefaRepositorio.findByUserId(any(UUID.class))).thenReturn(Optional.of(List.of(tarefa)));

        try{
            tarefaService.updateTarefa(tarefaDto);
        }catch (Exception e){
            throw new RuntimeException("Não foi possivel atualizar essa tarefa");
        }
    }
    @Test
    void whenUpadteThenReturnErroOfUserOrTarefaNotFound(){
        when(facade.getCurrentUser()).thenReturn(user);
        when(tarefaRepositorio.findByUserId(any(UUID.class))).thenReturn(Optional.of(List.of(tarefa)));
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));

        TarefaNotFoundException ex = assertThrows(TarefaNotFoundException.class, () -> tarefaService.updateTarefa(tarefaDto));
        assertEquals("ID tarefa não encotrado",ex.getMessage());


          when(tarefaRepositorio.findById(anyInt())).thenReturn(Optional.of(tarefa));
          when(tarefaRepositorio.findByUserId(any(UUID.class))).thenThrow(new NotFoundUserException("ID associado a tarefa, não encontrado."));

        NotFoundUserException erro = assertThrows(NotFoundUserException.class, () -> tarefaService.updateTarefa(tarefaDto));
        assertEquals("ID associado a tarefa, não encontrado.",erro.getMessage());

    }

    @Test
    void whenUpdateThenReturnErroSave(){
    }

    @Test
    void whenDeleteThenReturnSucesso(){
        when(tarefaRepositorio.findById(anyInt())).thenReturn(Optional.of(tarefa));
        when(facade.getCurrentUser()).thenReturn(user);

        try {
            tarefaService.deleteTarefa(tarefa.getId());
        }catch (Exception e){
            throw new IllegalArgument("Não foi possivel salvar");
        }
    }

    @Test
    void whenValidUserThenReturnAUser(){
        when(facade.getCurrentUser()).thenReturn(user);
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        User validUser = tarefaService.validUser();
        assertNotNull(validUser);
        assertEquals(user.getId(),tarefaService.validUser().getId());
    }

    @Test
    void whenValidUserThenReturnNotFound(){
        when(facade.getCurrentUser()).thenReturn(user);
        when(userRepository.findById(any(UUID.class))).thenThrow(new NotFoundUserException("Usuario não encontrado."));
        NotFoundUserException notFoundUserException = assertThrows(NotFoundUserException.class, () -> tarefaService.validUser());
        assertEquals("Usuario não encontrado.",notFoundUserException.getMessage());
    }

    private void inject(){
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(userService.findByIdUser(any(UUID.class))).thenReturn(user);
        when(tarefaRepositorio.findByUserId(user.getId())).thenReturn(Optional.of(List.of(tarefa)));
    }

    private void start(){
        user = new User();
        tarefa = new Tarefa();
        tarefaDto = new TarefaDto();
        user.setOnline(true);
        user.setEmail("Isaac@gmail");
        user.setId(UUID.randomUUID());
        user.setRole(RoleUser.USER);


        tarefa.setUser(user);
        tarefa.setId(1);
        tarefa.setNome("Comer");
        tarefa.setDataFinal(LocalDateTime.now());
        tarefa.setDataIniciado(LocalDateTime.now());
        tarefa.setQDiasCompletados(5);
        user.setTarefaList(List.of(tarefa));

        tarefaDto.setUserId(user);
        tarefaDto.setNome(tarefa.getNome());
        tarefaDto.setDataFinal(tarefa.getDataFinal());
        tarefaDto.setDataIniciado(tarefa.getDataIniciado());
        tarefaDto.setId(tarefa.getId());

    }
}