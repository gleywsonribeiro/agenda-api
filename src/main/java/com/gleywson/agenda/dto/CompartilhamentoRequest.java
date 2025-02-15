package com.gleywson.agenda.dto;

import com.gleywson.agenda.model.Permissao;

public class CompartilhamentoRequest {
    private String emailUsuario;
    private Permissao permissao;

    public CompartilhamentoRequest() {}

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }
}
