package com.gleywson.agenda.controller;

import com.gleywson.agenda.dto.CompartilhamentoRequest;
import com.gleywson.agenda.service.CompromissoCompartilhadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compromissos")
public class CompromissoCompartilhadoController {

    private final CompromissoCompartilhadoService compartilhadoService;

    public CompromissoCompartilhadoController(CompromissoCompartilhadoService compartilhadoService) {
        this.compartilhadoService = compartilhadoService;
    }

    @PostMapping("/{id}/compartilhar")
    public ResponseEntity<Void> compartilharCompromisso(@PathVariable Long id, @RequestBody CompartilhamentoRequest request) {
        compartilhadoService.compartilharCompromisso(id, request);
        return ResponseEntity.ok().build();
    }
}
