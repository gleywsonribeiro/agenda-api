package com.gleywson.agenda.service;

import com.gleywson.agenda.dto.CompartilhamentoRequest;
import com.gleywson.agenda.model.Compromisso;
import com.gleywson.agenda.model.CompromissoCompartilhado;
import com.gleywson.agenda.model.Permissao;
import com.gleywson.agenda.model.User;
import com.gleywson.agenda.repository.CompromissoCompartilhadoRepository;
import com.gleywson.agenda.repository.CompromissoRepository;
import com.gleywson.agenda.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CompromissoCompartilhadoService {

    private final CompromissoCompartilhadoRepository compartilhadoRepository;
    private final CompromissoRepository compromissoRepository;
    private final UserRepository userRepository;

    public CompromissoCompartilhadoService(CompromissoCompartilhadoRepository compartilhadoRepository,
                                           CompromissoRepository compromissoRepository,
                                           UserRepository userRepository) {
        this.compartilhadoRepository = compartilhadoRepository;
        this.compromissoRepository = compromissoRepository;
        this.userRepository = userRepository;
    }

    public void compartilharCompromisso(Long compromissoId, CompartilhamentoRequest request) {
        Compromisso compromisso = compromissoRepository.findById(compromissoId)
                .orElseThrow(() -> new RuntimeException("Compromisso não encontrado"));

        User usuario = userRepository.findByEmail(request.getEmailUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (compartilhadoRepository.existsByCompromissoAndUsuario(compromisso, usuario)) {
            throw new RuntimeException("Usuário já tem acesso a este compromisso");
        }

        CompromissoCompartilhado compartilhado = new CompromissoCompartilhado(compromisso, usuario, request.getPermissao());
        compartilhadoRepository.save(compartilhado);
    }
}
