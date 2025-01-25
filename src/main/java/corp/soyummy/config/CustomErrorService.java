package corp.soyummy.config;

import org.springframework.stereotype.Service;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

@Service
public class CustomErrorService {
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(
                    "{ \"status\": 401, \"error\": \"Unauthorized\", \"message\": \"Invalid name or password\" }"
            );
        };
    }

    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(
                    "{ \"status\": 403, \"error\": \"Access Denied\", \"message\": \"" + accessDeniedException.getMessage() + "\" }"
            );
        };
    }
}
