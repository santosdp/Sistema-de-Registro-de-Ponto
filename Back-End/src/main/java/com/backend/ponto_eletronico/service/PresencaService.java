package com.backend.ponto_eletronico.service;

import com.backend.ponto_eletronico.dto.DetalharPresencaDTO;
import com.backend.ponto_eletronico.dto.JustificarPresencaDTO;
import com.backend.ponto_eletronico.dto.ProcurarPresencaDTO;
import com.backend.ponto_eletronico.model.Presenca;
import com.backend.ponto_eletronico.model.Usuario;
import com.backend.ponto_eletronico.repository.PresencaRepository;
import com.backend.ponto_eletronico.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PresencaService {

  @Autowired
  private PresencaRepository presencaRepository;

  @Autowired
  public UsuarioRepository usuarioRepository;

  public DetalharPresencaDTO registrarPresenca(Long id) {
    LocalDate dia = LocalDate.now();
    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    Optional<Presenca> presencaOptional = presencaRepository.findByDiaAndUsuario(dia, usuario);
    Presenca presenca;
    if(presencaOptional.isPresent()) {
      presenca = presencaOptional.get();
    } else{
      presenca = new Presenca(dia);
      presenca.setUsuario(usuario);
      usuario.adicionarPresenca(presenca);
    }
    presenca.registrarPresenca();
    presencaRepository.save(presenca);

    return new DetalharPresencaDTO(presenca);
  }

  public DetalharPresencaDTO justificarPresenca(JustificarPresencaDTO justificarPresencaDTO) {
    Usuario usuario = usuarioRepository.findById(justificarPresencaDTO.id_usuario()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    Presenca presenca = presencaRepository.findByDiaAndUsuario(justificarPresencaDTO.dia(), usuario).orElseThrow(() -> new EntityNotFoundException("Presença não encontrada."));
    presenca.setJustificativa(justificarPresencaDTO.justificativa());
    presencaRepository.save(presenca);

    return new DetalharPresencaDTO(presenca);
  }

  public List<DetalharPresencaDTO> procurarPresenca(ProcurarPresencaDTO procurarPresencaDTO) {
    Usuario usuario = usuarioRepository.findById(procurarPresencaDTO.id_usuario()).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    return presencaRepository.findByUsuarioAndDiaBetween(usuario, procurarPresencaDTO.data_inicial(), procurarPresencaDTO.data_final()).stream().map(DetalharPresencaDTO::new).toList();
  }
}
