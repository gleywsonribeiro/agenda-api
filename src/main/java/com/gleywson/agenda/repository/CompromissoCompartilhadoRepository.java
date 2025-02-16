package com.gleywson.agenda.repository;

import com.gleywson.agenda.model.Compromisso;
import com.gleywson.agenda.model.CompromissoCompartilhado;
import com.gleywson.agenda.model.Permissao;
import com.gleywson.agenda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompromissoCompartilhadoRepository extends JpaRepository<CompromissoCompartilhado, Long> {
    /**
     * Busca todos os compartilhamentos de compromissos de um usuário
     * @param usuario
     * @return lista de compartilhamentos
     */
    List<CompromissoCompartilhado> findByUsuario(User usuario);

    /**
     * Verifica se já existe um compartilhamento para o compromisso e usuário informados
     * @param compromisso
     * @param usuario
     * @return true se já existe um compartilhamento, false caso contrário
     */
    boolean existsByCompromissoAndUsuario(Compromisso compromisso, User usuario);

    /**
     * Verifica se já existe um compartilhamento para o compromisso, usuário e permissão informados
     * @param compromisso
     * @param usuario
     * @param permissao
     * @return true se já existe um compartilhamento, false caso contrário
     */
    boolean existsByCompromissoAndUsuarioAndPermissao(Compromisso compromisso, User usuario, Permissao permissao);

    Optional<CompromissoCompartilhado> findByCompromissoAndUsuario(Compromisso compromisso, User usuario);

}
