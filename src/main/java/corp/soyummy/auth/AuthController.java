package corp.soyummy.auth;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

import corp.soyummy.auth.dto.LoginRequestDTO;
import corp.soyummy.auth.dto.RegisterRequestDTO;
import static corp.soyummy.constants.Constants.VERSION;

@RestController
@RequestMapping(VERSION + "auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //  POST  http://localhost:8080/api/v1/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequestDTO request,
            HttpServletResponse response) {
        Role role = (request.getRole() != null && (request.getRole().equals("ADMIN") || request.getRole().equals("USER"))) ?
                Role.valueOf(request.getRole()) : Role.USER;

        String token = authService.register(request, role);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
    }

    //  POST  http://localhost:8080/api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequestDTO request,
            HttpServletResponse response) {

        String token = authService.login(request);

        return ResponseEntity.ok(Map.of("token", token));
    }

    //  POST  http://localhost:8080/api/v1/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            HttpServletResponse response) {
        if (authHeader == null || authHeader.isBlank()) {
            return ResponseEntity.badRequest().body("Authorization header is missing");
        }

        authService.logout(authHeader);

        return ResponseEntity.ok("Logged out successfully");
    }
}
