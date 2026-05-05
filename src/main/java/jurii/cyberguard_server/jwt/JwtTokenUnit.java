package jurii.cyberguard_server.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jurii.cyberguard_server.entity.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUnit {
    private static final SecretKey SECTER_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    public String generateToken(User user) { // Теперь принимаем объект User
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name()); // Сохраняем роль (например, "ADMIN")

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECTER_KEY)
                .compact();
    }

    // Добавь метод для извлечения роли
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    public Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            Claims claims = extractAllClaims(token);

            if (claims.getExpiration() == null) {
                System.out.println("В токене нет даты");
                return extractedUsername.equals(username);
            }

            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECTER_KEY).parseClaimsJws(token).getBody();
    }
}
