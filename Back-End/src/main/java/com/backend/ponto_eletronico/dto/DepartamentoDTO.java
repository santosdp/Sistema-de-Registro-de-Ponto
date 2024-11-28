package com.backend.ponto_eletronico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartamentoDTO(

    @NotBlank(message = "Nome do departamento é obrigatório.")
    @Size(max = 100, message = "Nome ultrapassou 100 caracteres.")
    String nome,

    @NotNull(message = "Matricula do gerente é obrigatório.")
    @Size(min = 12, max = 12, message = "Matricula deve possuir 12 caracteres.")
    Long matricula_gerente
) {
}
