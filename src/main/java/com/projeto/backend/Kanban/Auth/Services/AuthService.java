package com.projeto.backend.Kanban.Auth.Services;

import com.projeto.backend.Kanban.Auth.DTOs.*;
import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtService jwtService, UserService userService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public AuthResponseDTO login(AuthLoginRequestDTO dto) {
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

    public AuthResponseDTO register(AuthRegisterRequestDTO dto) {
        User user = userService.createUserEntity(new UserRequestDTO(
                dto.name(),
                dto.email(),
                dto.password()
        ));

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
