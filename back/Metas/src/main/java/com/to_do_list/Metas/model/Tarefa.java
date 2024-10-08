package com.to_do_list.Metas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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

    public Tarefa(Integer id, String nome, LocalDateTime dataIniciado, LocalDateTime dataFinal, Integer qDiasCompletados) {
        this.id = id;
        this.nome = nome;
        this.dataIniciado = dataIniciado;
        this.dataFinal = dataFinal;
        this.qDiasCompletados = qDiasCompletados;
    }
}
