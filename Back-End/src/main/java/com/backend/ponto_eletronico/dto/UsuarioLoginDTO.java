package com.backend.ponto_eletronico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioLoginDTO(
    @NotBlank
    String login,

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$", message = "A senha deve conter letras minúsculas e maiúsculas, números, caracteres especiais e possuir entre 8 à 16 caracteres.")
    String senha) {
}
