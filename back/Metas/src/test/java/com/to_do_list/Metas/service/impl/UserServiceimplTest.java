package com.to_do_list.Metas.service.impl;

import com.to_do_list.Metas.model.Tarefa;
import com.to_do_list.Metas.model.User;
import com.to_do_list.Metas.model.dto.UserDto;
import com.to_do_list.Metas.model.dto.UserLoginDTO;
import com.to_do_list.Metas.model.role.RoleUser;
import com.to_do_list.Metas.repositorio.TarefaRepositorio;
import com.to_do_list.Metas.repositorio.UserRepository;
import com.to_do_list.Metas.service.AuthenticationService;
import com.to_do_list.Metas.service.exception.NotFoundUserException;
import com.to_do_list.Metas.service.exception.UserExistException;
import com.to_do_list.Metas.utils.AuthenticationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class UserServiceimplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceimpl userServiceimpl;
    @Mock
    private TarefaRepositorio tarefaRepositorio;
    @Mock
    private ModelMapper mapper;
    @Mock
    private AuthenticationFacade facade;
    @Mock
    private AuthenticationService authenticationService;
    private User user;
    private Tarefa tarefa;
    private UserDto userDto;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        start();
    }

    @Test
    void whenSaveUserThenReturnSucesso() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(mapper.map(any(UserDto.class), eq(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User usersave = userServiceimpl.saveUser(userDto);
        assertNotNull(usersave,"User não pode ser null");
        assertEquals(user.getEmail(),usersave.getEmail(),"os emails devem ser igual.");
        assertEquals(user.getRole(),usersave.getRole(), "As roles devem ser iguais.");

    }

    @Test
    void whenSaveUserThenReturnEmailJaCadastrado(){
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        UserExistException userExistException = assertThrows(UserExistException.class,
                () -> userServiceimpl.saveUser(userDto));
        assertEquals("Email já cadastrado no sistema.", userExistException.getMessage());
    }

    @Test
    void whenLoginUserThenReturnSucesso() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(authenticationService.login(any(UserLoginDTO.class))).thenReturn(String.valueOf(String.class));

        UserLoginDTO u = new UserLoginDTO();
        u.setEmail(user.getEmail());
        u.setPassword("123434");
        String token = (String) userServiceimpl.loginUser(u);
        assertNotNull(token);
        assertEquals(token.getClass(),String.class);

    }

    @Test
    void whenLoginUserThenReturnUserNotFound(){
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        UserLoginDTO u = new UserLoginDTO();
        u.setEmail(user.getEmail());
        u.setPassword("123434");
        NotFoundUserException notFoundUserException = assertThrows(NotFoundUserException.class,
                () -> userServiceimpl.loginUser(u));
        assertEquals("Usuario não encontrado.", notFoundUserException.getMessage());
    }

    @Test
    void perfil() {

    }

    @Test
    void findByIdUser() {
    }

    @Test
    void findByEmailUser() {
    }

    private void start(){
        user = new User();
        tarefa = new Tarefa();
        userDto = new UserDto();

        user.setOnline(true);
        user.setEmail("Isaac@gmail");
        user.setId(UUID.randomUUID());
        user.setRole(RoleUser.USER);
        user.setPassword("1234");

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setPassword(user.getPassword());

        tarefa.setUser(user);
        tarefa.setId(1);
        tarefa.setNome("Comer");
        tarefa.setDataFinal(LocalDateTime.now());
        tarefa.setDataIniciado(LocalDateTime.now());
        tarefa.setQDiasCompletados(5);
        user.setTarefaList(List.of(tarefa));

    }
}