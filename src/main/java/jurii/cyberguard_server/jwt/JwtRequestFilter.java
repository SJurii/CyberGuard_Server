package jurii.cyberguard_server.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUnit jwtTokenUnit;

    public JwtRequestFilter(JwtTokenUnit jwtTokenUnit) {
        this.jwtTokenUnit = jwtTokenUnit;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        System.out.println("--- ФИЛЬТР СРАБОТАЛ ДЛЯ URL: " + request.getRequestURI());
        System.out.println("Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtTokenUnit.extractUsername(jwt);
            } catch (Exception e) {
                // Если токен кривой — просто логируем и идем дальше.
                // Мы не устанавливаем аутентификацию, и Spring сам заблокирует
                // запрос позже, если этот эндпоинт защищен.
                logger.error("Не удалось извлечь username из JWT: " + e.getMessage());
            }
        }

        // Измени блок проверки внутри doFilterInternal:
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            boolean isValid = jwtTokenUnit.validateToken(jwt, username);
            System.out.println("--- Проверка токена для " + username + ": " + isValid);

            if (isValid) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("--- АУТЕНТИФИКАЦИЯ УСТАНОВЛЕНА В КОНТЕКСТ");
            } else {
                System.out.println("--- ОШИБКА: validateToken вернул false!");
            }
        } else {
            System.out.println("--- ПРОПУЩЕНО: username null или уже аутентифицирован");
        }
        filterChain.doFilter(request, response);
    }


}
