package jurii.cyberguard_server.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUnit {
    private static final SecretKey SECTER_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 часов
                .signWith(SignatureAlgorithm.HS512, SECTER_KEY)
                .compact();
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
