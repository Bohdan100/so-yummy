package corp.soyummy.config;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

import corp.soyummy.auth.AuthRepository;
import corp.soyummy.auth.User;

import static corp.soyummy.constants.Constants.VERSION;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthRepository authRepository;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith(VERSION + "auth/login") || request.getRequestURI().startsWith(VERSION + "auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response, "Unauthorized");
            return;
        }

        final String token = authHeader.substring(7);
        String email = jwtService.extractUserName(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> optionalUser = authRepository.findByToken(token);
            if (optionalUser.isEmpty()) {
                sendUnauthorizedResponse(response, "Invalid token");
                return;
            }

            User user = authRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (jwtService.isTokenValid(token, user)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new IllegalArgumentException("Invalid or expired token");
            }
        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = String.format("""
            {
                "status": 401,
                "error": "%s"
            }
            """, errorMessage);

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
