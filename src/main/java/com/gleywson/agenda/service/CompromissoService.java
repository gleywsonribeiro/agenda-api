package com.gleywson.agenda.service;

import com.gleywson.agenda.dto.CompromissoRequest;
import com.gleywson.agenda.model.Compromisso;
import com.gleywson.agenda.model.CompromissoCompartilhado;
import com.gleywson.agenda.model.Permissao;
import com.gleywson.agenda.model.User;
import com.gleywson.agenda.repository.CompromissoCompartilhadoRepository;
import com.gleywson.agenda.repository.CompromissoRepository;
import com.gleywson.agenda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompromissoService {

    private final CompromissoRepository compromissoRepository;
    private final UserRepository userRepository;

    @Autowired
    private CompromissoCompartilhadoRepository compartilhadoRepository;

    public CompromissoService(CompromissoRepository compromissoRepository, UserRepository userRepository) {
        this.compromissoRepository = compromissoRepository;
        this.userRepository = userRepository;
    }

    /**
     * Cria um compromisso
     * @param request dados do compromisso
     * @return compromisso criado
     */
    public Compromisso criarCompromisso(CompromissoRequest request) {
        String emailUsuario = getUsuarioAutenticado();
        User usuario = userRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Compromisso compromisso = new Compromisso();
        compromisso.setTitulo(request.getTitulo());
        compromisso.setDescricao(request.getDescricao());
        compromisso.setDataHoraInicio(request.getDataHoraInicio());
        compromisso.setDataHoraFim(request.getDataHoraFim());
        compromisso.setUsuario(usuario);

        return compromissoRepository.save(compromisso);
    }

    /**
     * Lista todos os compromissos do usuário autenticado (incluindo os compartilhados)
     * @return lista de compromissos
     */
    public List<Compromisso> listarCompromissos() {
        String emailUsuario = getUsuarioAutenticado();
        User usuario = userRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Busca compromissos do próprio usuário
        List<Compromisso> meusCompromissos = compromissoRepository.findByUsuario(usuario);

        // Busca compromissos compartilhados com o usuário
        List<Compromisso> compromissosCompartilhados = compartilhadoRepository.findByUsuario(usuario)
                .stream()
                .map(CompromissoCompartilhado::getCompromisso)
                .toList();

        // Junta os dois tipos de compromissos
        List<Compromisso> todosCompromissos = new ArrayList<>();
        todosCompromissos.addAll(meusCompromissos);
        todosCompromissos.addAll(compromissosCompartilhados);

        return todosCompromissos;
    }

    /**
     * Atualiza um compromisso
     * @param id identificador do compromisso
     * @param request dados do compromisso
     * @return compromisso atualizado
     */
    public Compromisso atualizarCompromisso(Long id, CompromissoRequest request) {
        String emailUsuario = getUsuarioAutenticado();
        User usuario = userRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Compromisso compromisso = compromissoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compromisso não encontrado"));

        // Se o usuário não for o dono, verifica se tem permissão de edição
        if (!compromisso.getUsuario().getId().equals(usuario.getId())) {
            boolean temPermissao = compartilhadoRepository.existsByCompromissoAndUsuarioAndPermissao(
                    compromisso, usuario, Permissao.EDITAR
            );
            if (!temPermissao) {
                throw new RuntimeException("Usuário não tem permissão para editar este compromisso");
            }
        }

        // Atualiza os campos permitidos
        compromisso.setTitulo(request.getTitulo());
        compromisso.setDescricao(request.getDescricao());
        compromisso.setDataHoraInicio(request.getDataHoraInicio());
        compromisso.setDataHoraFim(request.getDataHoraFim());

        return compromissoRepository.save(compromisso);
    }

    /**
     * Compartilha um compromisso com outro usuário
     * @param compromissoId identificador do compromisso
     * @param emailUsuario email do usuário com quem compartilhar
     */
    public void removerCompartilhamento(Long compromissoId, String emailUsuario) {
        String emailDono = getUsuarioAutenticado();

        Compromisso compromisso = compromissoRepository.findById(compromissoId)
                .orElseThrow(() -> new RuntimeException("Compromisso não encontrado"));

        // Verifica se o usuário autenticado é o dono do compromisso
        if (!compromisso.getUsuario().getEmail().equals(emailDono)) {
            throw new RuntimeException("Apenas o dono do compromisso pode remover compartilhamentos");
        }

        User usuario = userRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        CompromissoCompartilhado compartilhado = compartilhadoRepository.findByCompromissoAndUsuario(compromisso, usuario)
                .orElseThrow(() -> new RuntimeException("Compartilhamento não encontrado"));

        compartilhadoRepository.delete(compartilhado);
    }



    /**
     * Deleta um compromisso
     * @param id identificador do compromisso
     */
    public void deletarCompromisso(Long id) {
        String emailUsuario = getUsuarioAutenticado();
        User usuario = userRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Compromisso compromisso = compromissoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compromisso não encontrado"));

        // Verifica se o compromisso pertence ao usuário autenticado
        if (!compromisso.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Usuário não tem permissão para deletar este compromisso");
        }

        compromissoRepository.delete(compromisso);
    }

    public Compromisso buscarPorId(Long id) {
        String emailUsuario = getUsuarioAutenticado();
        User usuario = userRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Busca compromissos do próprio usuário
        return compromissoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Compromisso não encontrado"));
    }

    /**
     * Retorna o email do usuário autenticado
     * @return email do usuário
     */
    private String getUsuarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        throw new RuntimeException("Usuário não autenticado");
    }
}
