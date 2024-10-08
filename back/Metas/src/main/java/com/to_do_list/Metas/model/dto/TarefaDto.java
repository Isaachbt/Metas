package com.to_do_list.Metas.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TarefaDto{
    private Integer id;
    @NotBlank(message = "Necessario um nome.")
   private String nome;
    @NotBlank(message = "Necessario uma data inicial.")
   private LocalDateTime dataIniciado;
   private LocalDateTime dataFinal;
   private Integer qDiasCompletados;
}
