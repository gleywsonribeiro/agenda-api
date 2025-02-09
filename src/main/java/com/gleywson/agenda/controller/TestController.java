package com.gleywson.agenda.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/secure")
    public String secureEndpoint() {
        return "Acesso autorizado! Seu token JWT é válido.";
    }
}
