package com.projeto.backend.Kanban.Auth.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String password
) {}
