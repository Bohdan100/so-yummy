package corp.soyummy.auth;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import corp.soyummy.config.JwtService;

import corp.soyummy.auth.dto.LoginRequestDTO;
import corp.soyummy.auth.dto.RegisterRequestDTO;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequestDTO request, Role role) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        String token = jwtService.generateToken(user.getEmail());
        user.setToken(token);

        authRepository.save(user);

        return token;
    }

    public String login(LoginRequestDTO request) {
        User user = authRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());
        user.setToken(token);
        authRepository.save(user);

        return token;
    }

    public void logout(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token is required");
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new IllegalArgumentException("Invalid token format");
        }

        User user = authRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        user.setToken(null);

        authRepository.save(user);
    }
}
