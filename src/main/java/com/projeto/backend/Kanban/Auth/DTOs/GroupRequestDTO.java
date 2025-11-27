package com.projeto.backend.Kanban.Auth.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GroupRequestDTO(
        @NotBlank @NotNull
        String name
) {}