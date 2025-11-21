package com.projeto.backend.Kanban.Auth.DTOs;

public record AuthResponseDTO(
        String token,
        Long userId,
        String name,
        String email
) {}
