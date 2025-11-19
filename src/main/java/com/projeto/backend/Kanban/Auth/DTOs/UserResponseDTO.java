package com.projeto.backend.Kanban.Auth.DTOs;

public record UserResponseDTO(
        Long id,
        String name,
        String email
) {}
