package com.to_do_list.Metas.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.to_do_list.Metas.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TarefaDto{
    private Integer id;
    @NotBlank(message = "Necessario um nome.")
   private String nome;
    //@NotBlank(message = "Necessario uma data inicial.")
   private LocalDateTime dataIniciado;
   private LocalDateTime dataFinal;
   private Integer qDiasCompletados;
   private User userId;

    public TarefaDto(Integer id, String nome, LocalDateTime dataIniciado, LocalDateTime dataFinal, Integer qDiasCompletados, User userId) {
        this.id = id;
        this.nome = nome;
        this.dataIniciado = dataIniciado;
        this.dataFinal = dataFinal;
        this.qDiasCompletados = qDiasCompletados;
        this.userId = userId;
    }

    public TarefaDto() {
    }
}
