package com.to_do_list.Metas.service.impl;

import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.TarefaDto;
import com.to_do_list.Metas.model.role.RoleUser;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.TarefaNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class TarefaServiceImplTest {

    public static final int ID = 1;
    public static final String NOME = "Dormir";
    public static final LocalDateTime DATA_INICIADO = LocalDateTime.now();
    public static final LocalDateTime DATA_FINAL = LocalDateTime.now();
    public static final int USER_ID = 1;
    public static final String EMAIL = "Isaac@gmail";
    public static final String PASSWORD = "123";
    public static final RoleUser ROLE_USER = RoleUser.USER;

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
    private Optional<List<Tarefa>> tarefaList;
    private Optional<User> optionalUser;
    private User user;



    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    @DisplayName("Retornando uma lista de tarefas.")
    void whenfindAllTarefaUserThenReturnAnListTheAnTarefaInstance() {
        inject();
        List<Tarefa> list = tarefaService.findAllTarefaUser(1);
        assertTrue(tarefaList.isPresent());
        assertIterableEquals(list,tarefaList.get());
        assertEquals(list.getFirst().getUserId(),USER_ID);
    }

    @Test
    void whenFindByAllTarefaUserThenReturnAnNotFoundUserException(){
        when(userService.findByIdUser(anyInt())).thenThrow(new NotFoundUserException("Usuario não encontrado."));

             NotFoundUserException exception = assertThrows(NotFoundUserException.class, () ->
                tarefaService.findAllTarefaUser(userService.findByIdUser(ID).getId()));

             assertEquals("Usuario não encontrado.",exception.getMessage());

    }

    @Test
    void whenFindByAllTarefaUserThenReturnAnTarefaNotFoundException(){
        when(userService.findByIdUser(anyInt())).thenReturn(user);
        when(tarefaRepositorio.findByUserId(user.getId())).thenReturn(tarefaList);
        when(tarefaService.findAllTarefaUser(ID)).thenThrow(new TarefaNotFoundException("Crie sua primeira tarefa."));

        try{
            tarefaService.findAllTarefaUser(ID);
        }catch (Exception e){
            assertEquals(TarefaNotFoundException.class,e.getClass());
            assertEquals("Crie sua primeira tarefa.",e.getMessage());
        }
    }



    @Test
    @DisplayName("SalvandoTarefas")
    void saveTarefa(){
         inject();

         assertEquals(USER_ID,userService.findByIdUser(1).getId());
         TarefaDto tarefaDto = new TarefaDto();
         BeanUtils.copyProperties(tarefa,tarefaDto);
         assertEquals("Salvo com sucesso.",tarefaService.saveTatefa(tarefaDto));

    }



    private void inject(){
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        when(userService.findByIdUser(anyInt())).thenReturn(user);
        when(tarefaRepositorio.findByUserId(user.getId())).thenReturn(tarefaList);
    }

    private void start(){
        tarefaList = Optional.of(List.of(new Tarefa(ID, NOME, DATA_INICIADO, DATA_FINAL,9, USER_ID)));
        tarefa = new Tarefa(ID, NOME, DATA_INICIADO, DATA_FINAL,9, USER_ID);
        user = new User(USER_ID, EMAIL, PASSWORD, ROLE_USER);
        optionalUser = Optional.of(new User(USER_ID,EMAIL,PASSWORD,ROLE_USER));
    }
}