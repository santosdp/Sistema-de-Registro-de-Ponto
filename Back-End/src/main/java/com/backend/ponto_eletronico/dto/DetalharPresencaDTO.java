package com.backend.ponto_eletronico.dto;

import com.backend.ponto_eletronico.model.Presenca;

import java.time.LocalDate;
import java.time.LocalTime;

public record DetalharPresencaDTO(
    LocalDate dia,
    LocalTime hora_entrada,
    LocalTime hora_intervalo,
    LocalTime hora_retorno,
    LocalTime hora_saida,
    String justificativa
) {
  public DetalharPresencaDTO(Presenca presenca) {
    this(presenca.getDia(), presenca.getHora_entrada(), presenca.getHora_intervalo(), presenca.getHora_retorno(), presenca.getHora_saida(), presenca.getJustificativa());
  }
}
