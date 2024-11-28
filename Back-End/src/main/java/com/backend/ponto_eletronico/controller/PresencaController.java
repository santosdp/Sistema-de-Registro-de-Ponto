package com.backend.ponto_eletronico.controller;

import com.backend.ponto_eletronico.dto.DetalharPresencaDTO;
import com.backend.ponto_eletronico.dto.JustificarPresencaDTO;
import com.backend.ponto_eletronico.dto.ProcurarPresencaDTO;
import com.backend.ponto_eletronico.model.Presenca;
import com.backend.ponto_eletronico.service.PresencaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("presenca")
public class PresencaController {

  @Autowired
  private PresencaService presencaService;

  @GetMapping("/{id}")
  @Transactional
  public ResponseEntity<DetalharPresencaDTO> registrarPresenca(@PathVariable Long id) {
    DetalharPresencaDTO presenca = presencaService.registrarPresenca(id);

    return ResponseEntity.ok().body(presenca);
  }

  @PostMapping("/justificar")
  @Transactional
  public ResponseEntity<DetalharPresencaDTO> justificarPresenca(@RequestBody @Valid JustificarPresencaDTO justificarPresencaDTO) {
    DetalharPresencaDTO presenca = presencaService.justificarPresenca(justificarPresencaDTO);

    return ResponseEntity.ok().body(presenca);
  }

  @PostMapping
  @Transactional
  public ResponseEntity<List<DetalharPresencaDTO>> procurarPresencas(@RequestBody @Valid ProcurarPresencaDTO procurarPresencaDTO) {
    List<DetalharPresencaDTO> presencas = presencaService.procurarPresenca(procurarPresencaDTO);

    return ResponseEntity.ok().body(presencas);
  }
}
