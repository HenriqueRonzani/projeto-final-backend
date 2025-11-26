package com.projeto.backend.Kanban.Board.DTOs;

public record CardQueryRequestDTO(
        String title,
        String status,
        String start,
        String end,
        Long creatorId,
        Long userId,
        Long tabId
) {}

