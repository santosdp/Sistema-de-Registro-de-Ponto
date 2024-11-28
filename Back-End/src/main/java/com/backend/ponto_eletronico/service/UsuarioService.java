package com.backend.ponto_eletronico.service;

import com.backend.ponto_eletronico.config.security.SecurityConfiguration;
import com.backend.ponto_eletronico.dto.AtualizarUsuarioDTO;
import com.backend.ponto_eletronico.dto.DetalhamentoUsuarioDTO;
import com.backend.ponto_eletronico.dto.UsuarioDTO;
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
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private DepartamentoRepository departamentoRepository;

  @Autowired
  private SecurityConfiguration securityConfiguration;

  public DetalhamentoUsuarioDTO criarUsuario(UsuarioDTO UsuarioDTO) {
    Departamento usuarioDepartamento = departamentoRepository.findById(UsuarioDTO.id_departamento()).orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado."));
    Usuario usuario = new Usuario(
        UsuarioDTO.nome(),
        UsuarioDTO.matricula(),
        UsuarioDTO.telefone(),
        UsuarioDTO.email(),
        securityConfiguration.passwordEncoder().encode(UsuarioDTO.senha())
    );
    usuario.setDepartamento(usuarioDepartamento);
    usuarioDepartamento.addUsuario(usuario);
    usuarioRepository.save(usuario);

    return new DetalhamentoUsuarioDTO(usuario);
  }

  public DetalhamentoUsuarioDTO detalharUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    return new DetalhamentoUsuarioDTO(usuario);
  }

  public Page<DetalhamentoUsuarioDTO> listarUsuarios(Pageable paginacao) {
    return usuarioRepository.findAllByAtivoTrue(paginacao).map(DetalhamentoUsuarioDTO::new);
  }

  public DetalhamentoUsuarioDTO atualizarUsuario(AtualizarUsuarioDTO atualizarUsuarioDTO) {
    Usuario usuario = usuarioRepository.findById(atualizarUsuarioDTO.id()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    if (atualizarUsuarioDTO.id_departamento() != null) {
      Departamento departamento = departamentoRepository.findById(atualizarUsuarioDTO.id_departamento()).orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado"));
      usuario.atualizarUsuario(atualizarUsuarioDTO, departamento);
    } else {
      usuario.atualizarUsuario(atualizarUsuarioDTO, null);
    }
    usuarioRepository.save(usuario);

    return new DetalhamentoUsuarioDTO(usuario);
  }

  public void deletarUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    usuario.excluir();
    usuarioRepository.save(usuario);
  }

  public DetalhamentoUsuarioDTO promoverUsuario(Long id) {
    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    usuario.promoverUsuario();
    usuarioRepository.save(usuario);
    return new DetalhamentoUsuarioDTO(usuario);
  }
}
