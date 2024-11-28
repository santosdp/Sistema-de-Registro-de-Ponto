package com.backend.ponto_eletronico.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.backend.ponto_eletronico.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${api.security.token.issuer}")
  private String ISSUER;

  @Value("${api.security.token.secret}")
  private String secret;

  public String gerarToken(Usuario usuario) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer(ISSUER)
          .withSubject(usuario.getUsername())
          .withExpiresAt(dataExpiracao())
          .sign(algorithm);
    } catch (JWTCreationException exception){
      throw new RuntimeException("Erro ao gerar token JWT", exception);
    }
  }

  public String getSubject(String tokenJWT) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer(ISSUER)
          .build()
          .verify(tokenJWT)
          .getSubject();
    } catch (JWTVerificationException exception){
      throw new RuntimeException("Token JWT inválido ou expirado!");
    }
  }

  private Instant dataExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00"));
  }
}
