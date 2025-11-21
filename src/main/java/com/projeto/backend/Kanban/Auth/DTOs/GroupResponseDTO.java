package com.projeto.backend.Kanban.Auth.DTOs;

import java.util.List;

public record GroupResponseDTO(
        Long id,
        String name,
        List<Long> userIds
) {}