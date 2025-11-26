package com.projeto.backend.Kanban.Board.DTOs;

import com.projeto.backend.Kanban.Board.Enums.TabActionOnMove;

public record TabResponseDTO(
        Long id,
        String name,
        String color,
        TabActionOnMove actionOnMove,
        Long groupId
) {}
