package com.backend.ponto_eletronico.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AtualizarUsuarioDTO(

  @NotNull
  Long id,

  @Size(max = 100, message = "Nome ultrapassou 100 caracteres.")
  String nome,

  @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}[\\s-]?\\d{4}$\n", message = "Apenas telefones brasileiros são aceitos.")
  String telefone,

  @Size(max = 50, message = "E-mail ultrapassou 50 caracteres.")
  @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@tjam\\.jus\\.br$\n", message = "Obrigatório utilizar e-mail do TJAM.")
  String email,

  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$", message = "A senha deve conter letras minúsculas e maiúsculas, números, caracteres especiais e possuir entre 8 à 16 caracteres.")
  String senha,

  Long id_departamento
) {
}
