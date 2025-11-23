package com.projeto.backend.Kanban.Auth.Controllers;

import com.projeto.backend.Kanban.Auth.DTOs.AuthLoginRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.AuthRegisterRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.AuthResponseDTO;
import com.projeto.backend.Kanban.Auth.Services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthLoginRequestDTO dto) {
        return authService.login(dto);
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody AuthRegisterRequestDTO dto) {
        return authService.register(dto);
    }
}
