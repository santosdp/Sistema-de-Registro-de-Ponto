package com.backend.ponto_eletronico.controller;

import com.backend.ponto_eletronico.dto.AtualizarUsuarioDTO;
import com.backend.ponto_eletronico.dto.DetalhamentoUsuarioDTO;
import com.backend.ponto_eletronico.dto.UsuarioDTO;
import com.backend.ponto_eletronico.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping
  @Transactional
  public ResponseEntity<DetalhamentoUsuarioDTO> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO, UriComponentsBuilder uriBuilder){
    var usuario = usuarioService.criarUsuario(usuarioDTO);
    var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(usuario.id()).toUri();

    return ResponseEntity.created(uri).body(usuario);
  }

  @GetMapping
  public ResponseEntity<Page<DetalhamentoUsuarioDTO>> listarUsuarios(@PageableDefault(size = 10, sort = {"nome"})Pageable paginacao) {
    var page = usuarioService.listarUsuarios(paginacao);

    return ResponseEntity.ok().body(page);
  }

  @PutMapping
  @Transactional
  public ResponseEntity<DetalhamentoUsuarioDTO> atualizarUsuario(@RequestBody @Valid AtualizarUsuarioDTO usuarioDTO) {
    var usuario = usuarioService.atualizarUsuario(usuarioDTO);

    return ResponseEntity.ok().body(usuario);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
    usuarioService.deletarUsuario(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetalhamentoUsuarioDTO> detalharUsuario(@PathVariable Long id) {
    var usuario = usuarioService.detalharUsuario(id);

    return ResponseEntity.ok().body(usuario);
  }
}
