package com.backend.ponto_eletronico.dto;

import com.backend.ponto_eletronico.model.Departamento;

public record DetalhamentoDepartamentoDTO(Long id_departamento, String nome_departamento, String nome_gerente) {
  public DetalhamentoDepartamentoDTO(Departamento departamento){
    this(departamento.getId_departamento(), departamento.getNome_departamento(), departamento.getGerente().getNome());
  }
}
