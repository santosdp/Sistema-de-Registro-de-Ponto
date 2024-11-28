package com.backend.ponto_eletronico.controller;

import com.backend.ponto_eletronico.model.Usuario;
import com.backend.ponto_eletronico.dto.UsuarioLoginDTO;
import com.backend.ponto_eletronico.config.security.TokenDadosJWT;
import com.backend.ponto_eletronico.config.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

  @Autowired
  private AuthenticationManager manager;

  @Autowired
  private TokenService tokenService;

  @PostMapping
  public ResponseEntity<TokenDadosJWT> efetuarLogin(@RequestBody @Valid UsuarioLoginDTO request) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(request.login(), request.senha());
    var authentication = manager.authenticate(authenticationToken);

    var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

    return new ResponseEntity<>(new TokenDadosJWT(tokenJWT), HttpStatus.OK);
  }
}
