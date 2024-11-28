package com.backend.ponto_eletronico.config.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Void> tratarErro404() {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
    var erros = ex.getFieldErrors();
    var errosLista = erros.stream().map(DadosErrosValidacao::new).toList();
    return new ResponseEntity<>(errosLista, HttpStatus.BAD_REQUEST);
  }

  private record DadosErrosValidacao(String campo, String mensagem) {
    public DadosErrosValidacao(FieldError erro) {
      this(erro.getField(), erro.getDefaultMessage());
    }
  }
}
