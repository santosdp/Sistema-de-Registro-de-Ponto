package com.backend.ponto_eletronico.repository;

import com.backend.ponto_eletronico.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  UserDetails findByEmail(String email);

  Page<Usuario> findAllByAtivoTrue(Pageable paginacao);

  Optional<Usuario> findByMatricula(Long matricula);
}
