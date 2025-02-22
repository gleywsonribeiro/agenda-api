package com.gleywson.agenda.security;

import com.gleywson.agenda.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "ZnYLeNpJei2skT08Y/JCjdrKmwbZZxt2mNN2gULpHfMslyqP285QYR0y6NpeJVaEBf/kzjorCnbUOWfQy3iiOw=="; // Deve ter pelo menos 256 bits
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("nome", user.getNome())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey()) // Usa a chave correta para assinar
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Agora usa `.verifyWith()` corretamente
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extrairNome(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Agora usa `.verifyWith()` corretamente
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("nome", String.class); // Extrai o nome do payload
    }


    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey()) // Usa `.verifyWith()` para validar
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
