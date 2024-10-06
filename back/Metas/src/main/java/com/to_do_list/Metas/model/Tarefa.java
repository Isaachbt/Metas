package com.to_do_list.Metas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "tb_tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private LocalDateTime dataIniciado;
    private LocalDateTime dataFinal;
    private Integer qDiasCompletados;

}
