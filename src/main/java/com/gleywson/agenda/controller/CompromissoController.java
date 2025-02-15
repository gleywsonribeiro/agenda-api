package com.gleywson.agenda.controller;

import com.gleywson.agenda.dto.CompromissoRequest;
import com.gleywson.agenda.model.Compromisso;
import com.gleywson.agenda.service.CompromissoService;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compromissos")
public class CompromissoController {

    private final CompromissoService compromissoService;

    public CompromissoController(CompromissoService compromissoService) {
        this.compromissoService = compromissoService;
    }

    @PostMapping
    public ResponseEntity<Compromisso> criarCompromisso(@RequestBody CompromissoRequest request) {
        return ResponseEntity.ok(compromissoService.criarCompromisso(request));
    }

    @GetMapping
    public ResponseEntity<List<Compromisso>> listarCompromissos() {
        return ResponseEntity.ok(compromissoService.listarCompromissosDoUsuario());
    }
}
