package com.backend.ponto_eletronico.controller;

import com.backend.ponto_eletronico.dto.AtualizarDepartamentoDTO;
import com.backend.ponto_eletronico.dto.DepartamentoDTO;
import com.backend.ponto_eletronico.dto.DetalhamentoDepartamentoDTO;
import com.backend.ponto_eletronico.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("departamento")
public class DepartamentoController {

  @Autowired
  DepartamentoService departamentoService;

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Transactional
  public ResponseEntity<DetalhamentoDepartamentoDTO> criarDepartamento(@RequestBody @Valid DepartamentoDTO departamentoDTO, UriComponentsBuilder uriBuilder) {
    var departamento = departamentoService.criarDepartamento(departamentoDTO);
    var uri = uriBuilder.path("/departamento/{id}").buildAndExpand(departamento.id_departamento()).toUri();

    return ResponseEntity.created(uri).body(departamento);
  }

  @GetMapping
  public ResponseEntity<Page<DetalhamentoDepartamentoDTO>> listarDepartamentos(@PageableDefault(size = 10, sort = {"nome_departamento"}) Pageable paginacao) {
    var page = departamentoService.listarDepartamentos(paginacao);
    return ResponseEntity.ok().body(page);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetalhamentoDepartamentoDTO> detalharDepartamento(@PathVariable Long id){
    var departamento = departamentoService.detalharDepartamento(id);
    return ResponseEntity.ok().body(departamento);
  }

  @PutMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @Transactional
  public ResponseEntity<DetalhamentoDepartamentoDTO> atualizarDepartamento(@RequestBody @Valid AtualizarDepartamentoDTO atualizarDepartamentoDTO) {
    var departamento = departamentoService.atualizarDepartamento(atualizarDepartamentoDTO);
    return ResponseEntity.ok().body(departamento);
  }
}
