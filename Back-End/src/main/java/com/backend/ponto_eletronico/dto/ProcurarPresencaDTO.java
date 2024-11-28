package com.backend.ponto_eletronico.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProcurarPresencaDTO(

    @NotNull
    Long id_usuario,

    @NotNull
    LocalDate data_inicial,

    @NotNull
    LocalDate data_final
) {
}
