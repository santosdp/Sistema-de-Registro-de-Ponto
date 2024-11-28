package com.backend.ponto_eletronico.model;

import com.backend.ponto_eletronico.dto.AtualizarDepartamentoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "departamentos")
@Entity(name = "departamento")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id_departamento")
public class Departamento {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_departamento;

  private String nome_departamento;

  @OneToMany(mappedBy = "departamento", cascade = CascadeType.PERSIST)
  private Set<Usuario> usuarios = new HashSet<>();

  @OneToOne
  @JoinColumn(name = "id_gerente", nullable = true)
  private Usuario gerente;

  public void addUsuario(Usuario usuario) {
    this.usuarios.add(usuario);
    usuario.setDepartamento(this);
  }

  public void removeUsuario(Usuario usuario) {
    this.usuarios.remove(usuario);
    usuario.setDepartamento(null);
  }

  public void setGerente(Usuario gerente) {
    if (gerente != null) {
      this.gerente = gerente;
      gerente.setGerenteDoDepartamento(this);
    }
  }

  public void atualizarDepartamento(AtualizarDepartamentoDTO atualizarDepartamentoDTO, Usuario gerente) {
    if(atualizarDepartamentoDTO.nome() != null) {
      this.nome_departamento = atualizarDepartamentoDTO.nome();
    }
    if(gerente != null) {
      gerente.promoverUsuario();
      gerente.setDepartamento(this);
      this.setGerente(gerente);
    }
  }
}
