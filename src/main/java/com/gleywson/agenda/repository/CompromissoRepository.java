package com.gleywson.agenda.repository;

import com.gleywson.agenda.model.Compromisso;
import com.gleywson.agenda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompromissoRepository extends JpaRepository<Compromisso, Long> {
    List<Compromisso> findByUsuario(User usuario); // Busca compromissos por usuário
}
