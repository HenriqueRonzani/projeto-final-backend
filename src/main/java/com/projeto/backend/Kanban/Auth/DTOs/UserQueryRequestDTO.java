package com.projeto.backend.Kanban.Auth.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UserQueryRequestDTO(
        @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres.")
        String name,

        String email,
        @Positive(message = "O ID do grupo deve ser um número positivo.")
        Long groupId,
        @Positive(message = "O ID do card deve ser um número positivo.")
        Long cardId
) {}
