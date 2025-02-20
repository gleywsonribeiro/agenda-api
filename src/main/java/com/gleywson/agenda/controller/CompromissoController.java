package com.gleywson.agenda.controller;

import com.gleywson.agenda.dto.CompromissoRequest;
import com.gleywson.agenda.model.Compromisso;
import com.gleywson.agenda.service.CompromissoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Compromisso> criarCompromisso(@RequestBody @Valid CompromissoRequest request) {
        return ResponseEntity.ok(compromissoService.criarCompromisso(request));
    }

    @GetMapping
    public ResponseEntity<List<Compromisso>> listarCompromissos() {
        return ResponseEntity.ok(compromissoService.listarCompromissos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compromisso> atualizarCompromisso(@PathVariable Long id, @RequestBody @Valid CompromissoRequest request) {
        return ResponseEntity.ok(compromissoService.atualizarCompromisso(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCompromisso(@PathVariable Long id) {
        compromissoService.deletarCompromisso(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/remover-compartilhamento")
    public ResponseEntity<Void> removerCompartilhamento(@PathVariable Long id, @RequestParam String emailUsuario) {
        compromissoService.removerCompartilhamento(id, emailUsuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compromisso> buscarPorId(@PathVariable Long id) {
        Compromisso compromisso = compromissoService.buscarPorId(id);
        return ResponseEntity.ok(compromisso);
    }
}
