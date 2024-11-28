package com.backend.ponto_eletronico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record JustificarPresencaDTO (
    @NotNull
    Long id_usuario,

    @NotNull
    LocalDate dia,

    @NotBlank
    String justificativa
) {
}
