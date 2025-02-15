package com.gleywson.agenda.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compromisso_compartilhado")
public class CompromissoCompartilhado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compromisso_id", nullable = false)
    private Compromisso compromisso;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @Enumerated(EnumType.STRING)
    private Permissao permissao;

    public CompromissoCompartilhado() {}

    public CompromissoCompartilhado(Compromisso compromisso, User usuario, Permissao permissao) {
        this.compromisso = compromisso;
        this.usuario = usuario;
        this.permissao = permissao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compromisso getCompromisso() {
        return compromisso;
    }

    public void setCompromisso(Compromisso compromisso) {
        this.compromisso = compromisso;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }
}
