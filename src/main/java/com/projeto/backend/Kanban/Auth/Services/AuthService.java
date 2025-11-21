package com.projeto.backend.Kanban.Auth.Services;

import com.projeto.backend.Kanban.Auth.DTOs.AuthRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.AuthResponseDTO;
import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO login(AuthRequestDTO dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Credenciais invalidas"));

        if (!encoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Credenciais invalidas");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
