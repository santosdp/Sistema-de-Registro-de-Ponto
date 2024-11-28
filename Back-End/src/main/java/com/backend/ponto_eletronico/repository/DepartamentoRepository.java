package com.backend.ponto_eletronico.repository;

import com.backend.ponto_eletronico.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
