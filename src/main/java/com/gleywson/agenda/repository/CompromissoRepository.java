package com.gleywson.agenda.repository;

import com.gleywson.agenda.model.Compromisso;
import com.gleywson.agenda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompromissoRepository extends JpaRepository<Compromisso, Long> {
    List<Compromisso> findByUsuario(User usuario); // Busca compromissos por usuário
    Optional<Compromisso> findByIdAndUsuario(Long id, User usuario); // Busca compromisso por id e usuário
}
