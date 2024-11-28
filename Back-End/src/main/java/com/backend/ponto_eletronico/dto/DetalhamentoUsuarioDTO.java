package com.backend.ponto_eletronico.dto;

import com.backend.ponto_eletronico.model.Cargo;
import com.backend.ponto_eletronico.model.Usuario;

public record DetalhamentoUsuarioDTO(
    Long id,
    String nome,
    Long matricula,
    String email,
    Cargo cargo,
    DetalhamentoDepartamentoDTO departamento) {
  public DetalhamentoUsuarioDTO(Usuario usuario) {
    this(usuario.getId(), usuario.getNome(), usuario.getMatricula(), usuario.getEmail(), usuario.getCargo(), new DetalhamentoDepartamentoDTO(usuario.getDepartamento().getId_departamento(), usuario.getDepartamento().getNome_departamento(), usuario.getDepartamento().getGerente().getNome()));
  }
}
