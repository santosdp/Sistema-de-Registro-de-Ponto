package com.backend.ponto_eletronico.service;

import com.backend.ponto_eletronico.dto.AtualizarDepartamentoDTO;
import com.backend.ponto_eletronico.dto.DepartamentoDTO;
import com.backend.ponto_eletronico.dto.DetalhamentoDepartamentoDTO;
import com.backend.ponto_eletronico.dto.DetalhamentoUsuarioDTO;
import com.backend.ponto_eletronico.model.Departamento;
import com.backend.ponto_eletronico.model.Usuario;
import com.backend.ponto_eletronico.repository.DepartamentoRepository;
import com.backend.ponto_eletronico.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private DepartamentoRepository departamentoRepository;

  public DetalhamentoDepartamentoDTO criarDepartamento(DepartamentoDTO departamentoDTO) {
    Usuario usuario = usuarioRepository.findByMatricula(departamentoDTO.matricula_gerente()).orElseThrow(() -> new EntityNotFoundException("Matricula de gerente não encontrado"));
    Departamento departamento = new Departamento(
        null,
        departamentoDTO.nome(),
        null,
        null
    );
    departamento.addUsuario(usuario);
    departamento.setGerente(usuario);
    departamentoRepository.save(departamento);
    return new DetalhamentoDepartamentoDTO(departamento);
  }

  public DetalhamentoDepartamentoDTO detalharDepartamento(Long id) {
    Departamento departamento = departamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
    return new DetalhamentoDepartamentoDTO(departamento);
  }

  public Page<DetalhamentoDepartamentoDTO> listarDepartamentos(Pageable paginacao) {
    return departamentoRepository.findAll(paginacao).map(DetalhamentoDepartamentoDTO::new);
  }

  public DetalhamentoDepartamentoDTO atualizarDepartamento(AtualizarDepartamentoDTO atualizarDepartamentoDTO) {
    Departamento departamento = departamentoRepository.findById(atualizarDepartamentoDTO.id()).orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
    if(atualizarDepartamentoDTO.matricula_gerente() != null) {
      Usuario gerente = usuarioRepository.findByMatricula(atualizarDepartamentoDTO.matricula_gerente()).orElseThrow(() -> new EntityNotFoundException("Matricula não corresponde a nenhum usuário"));
      departamento.atualizarDepartamento(atualizarDepartamentoDTO, gerente);
    } else {
      departamento.atualizarDepartamento(atualizarDepartamentoDTO, null);
    }
    departamentoRepository.save(departamento);

    return new DetalhamentoDepartamentoDTO(departamento);
  }

}