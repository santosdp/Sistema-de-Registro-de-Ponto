package com.backend.ponto_eletronico.repository;

import com.backend.ponto_eletronico.model.Presenca;
import com.backend.ponto_eletronico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PresencaRepository extends JpaRepository<Presenca, Long> {
  Optional<Presenca> findByDia(LocalDate dia);

  Optional<Presenca> findByDiaAndUsuario(LocalDate dia, Usuario usuario);

  List<Presenca> findByUsuarioAndDiaBetween(Usuario usuario, LocalDate localDate, LocalDate localDate1);
}
