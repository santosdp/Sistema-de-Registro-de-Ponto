package com.backend.ponto_eletronico.model;

import com.backend.ponto_eletronico.dto.AtualizarUsuarioDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private Long matricula;
  private String telefone;
  private String email;
  private String senha;

  @Enumerated(EnumType.STRING)
  private Cargo cargo;

  @ManyToOne
  @JoinColumn(name = "id_departamento", nullable = true)
  private Departamento departamento;

  @OneToOne(mappedBy = "gerente", cascade = CascadeType.PERSIST)
  private Departamento gerenteDoDepartamento;

  private Boolean ativo;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Presenca> presencas = new ArrayList<>();

  public Usuario(String nome, Long matricula, String telefone, String email, String senha){
    this.nome = nome;
    this.matricula = matricula;
    this.telefone = telefone;
    this.email = email;
    this.senha = senha;
    this.cargo = Cargo.ROLE_USER;
    this.ativo = true;
    this.gerenteDoDepartamento = null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.cargo.toString());
    return List.of(authority);
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.ativo;
  }

  public void excluir() {
    this.ativo = false;
  }

  public void atualizarUsuario(AtualizarUsuarioDTO dados, Departamento departamento) {
    if (dados.nome() != null) {
      this.nome = dados.nome();
    }
    if (dados.telefone() != null) {
      this.telefone = dados.telefone();
    }
    if (dados.email() != null) {
      this.email = dados.email();
    }
    if (dados.senha() != null) {
      this.senha = dados.senha();
    }
    if (departamento != null) {
      this.departamento = departamento;
    }
  }

  public void promoverUsuario() {
    this.departamento.getGerente().rebaixarUsuario();
    this.cargo = Cargo.ROLE_ADMIN;
  }

  public void rebaixarUsuario() {
    this.gerenteDoDepartamento = null;
    this.cargo = Cargo.ROLE_USER;
  }

  public void setDepartamento(Departamento departamento) {
    if (this.departamento != null) {
      this.departamento.getUsuarios().remove(this);
    }
    this.departamento = departamento;
    if (departamento != null) {
      departamento.getUsuarios().add(this);
    }
  }

  public void setGerenteDoDepartamento(Departamento departamento) {
    if (departamento != null) {
      this.gerenteDoDepartamento = departamento;
      departamento.setGerente(this);
    }
  }

  public void adicionarPresenca(Presenca presenca) {
    presencas.add(presenca);
    presenca.setUsuario(this);
  }
}
