package com.gleywson.agenda.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String nome;
    private String email;
    private String password;
}
