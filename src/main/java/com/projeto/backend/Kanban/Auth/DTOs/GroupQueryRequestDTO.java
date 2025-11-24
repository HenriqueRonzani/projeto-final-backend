package com.projeto.backend.Kanban.Auth.DTOs;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public record GroupQueryRequestDTO(
        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres.")
        String name,
        @Positive(message = "O ID do usuário deve ser um número positivo.")
        Long userId
) {}
