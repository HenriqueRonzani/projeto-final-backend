package com.projeto.backend.Kanban.Board.DTOs;

public record TabQueryRequestDTO(
        String name,
        String color,
        String actionOnMove,
        Long groupId
) {}
