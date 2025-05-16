package com.chat.chat.security.filter;

import com.chat.chat.security.User;
import com.chat.chat.security.service.AuthService;
import com.chat.chat.security.utils.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final JWT jsonWebTokenService;
    private final AuthService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // если токена нет, просто пропускаем дальше — SecurityConfig настроит, где нужно auth
        }

        try {
            String token = authorization.substring(7);

            if (jsonWebTokenService.isTokenExpired(token)) {
                throw new ServletException("Invalid token");
            }

            String username = jsonWebTokenService.getUsernameFromToken(token);
            User user = authenticationService.getUser(username);

            // Можно положить пользователя в SecurityContext (по желанию)
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            // И/или в атрибут запроса, если нужно
            request.setAttribute("authenticatedUser", user);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Invalid authentication token, or token missing.\"}");
        }
    }
}
