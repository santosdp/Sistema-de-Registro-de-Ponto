package com.backend.ponto_eletronico.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizarDepartamentoDTO(
    @NotNull
    Long id,

    @Size(max = 100, message = "Nome ultrapassou 100 caracteres.")
    String nome,

    @Size(min = 12, max = 12, message = "Matricula deve possuir 12 caracteres.")
    Long matricula_gerente
) {
}
