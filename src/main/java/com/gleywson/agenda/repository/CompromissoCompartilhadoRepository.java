package com.gleywson.agenda.repository;

import com.gleywson.agenda.model.Compromisso;
import com.gleywson.agenda.model.CompromissoCompartilhado;
import com.gleywson.agenda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompromissoCompartilhadoRepository extends JpaRepository<CompromissoCompartilhado, Long> {
    List<CompromissoCompartilhado> findByUsuario(User usuario);
    boolean existsByCompromissoAndUsuario(Compromisso compromisso, User usuario);
}
