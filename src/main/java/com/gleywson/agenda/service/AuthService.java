package com.gleywson.agenda.service;

import com.gleywson.agenda.dto.AuthRequest;
import com.gleywson.agenda.dto.AuthResponse;
import com.gleywson.agenda.dto.RegisterRequest;
import com.gleywson.agenda.model.Role;
import com.gleywson.agenda.model.User;
import com.gleywson.agenda.repository.UserRepository;
import com.gleywson.agenda.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        // Verifica se o usuário já existe
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        // Cria o novo usuário e salva no banco
        User user = new User();
        user.setNome(request.getNome());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Criptografa a senha
        user.setRole(Role.USER);
        userRepository.save(user);

        // Gera um token JWT
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // Verifica a senha
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta!");
        }

        // Gera o token JWT
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
