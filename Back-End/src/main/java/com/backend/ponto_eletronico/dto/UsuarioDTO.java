package com.backend.ponto_eletronico.dto;

import com.backend.ponto_eletronico.model.Cargo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 100, message = "Nome ultrapassou 100 caracteres.")
    String nome,

    @NotNull(message = "Matricula é obrigatório.")
    @Size(min = 12, max = 12, message = "Matricula deve possuir 12 caracteres.")
    Long matricula,

    @NotBlank(message = "E-mail é obrigatório.")
    @Size(max = 50, message = "E-mail ultrapassou 50 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@tjam\\.jus\\.br$\n", message = "Obrigatório utilizar e-mail do TJAM.")
    String email,

    @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$\n", message = "Apenas telefones brasileiros são aceitos.")
    String telefone,

    @NotBlank(message = "Senha é obrigatório.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$", message = "A senha deve conter letras minúsculas e maiúsculas, números, caracteres especiais e possuir entre 8 à 16 caracteres.")
    String senha,

    @NotNull(message = "Id do Departamento é obrigatório.")
    Long id_departamento
) {
}
