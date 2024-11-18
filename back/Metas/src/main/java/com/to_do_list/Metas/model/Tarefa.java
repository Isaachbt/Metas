package com.to_do_list.Metas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference
    @ManyToOne
    private User user;

    public Tarefa(Integer id, String nome, LocalDateTime dataIniciado, LocalDateTime dataFinal, Integer qDiasCompletados, User user) {
        this.id = id;
        this.nome = nome;
        this.dataIniciado = dataIniciado;
        this.dataFinal = dataFinal;
        this.qDiasCompletados = qDiasCompletados;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataIniciado=" + dataIniciado +
                ", dataFinal=" + dataFinal +
                ", qDiasCompletados=" + qDiasCompletados +
                ", user=" + user +
                '}';
    }
}
