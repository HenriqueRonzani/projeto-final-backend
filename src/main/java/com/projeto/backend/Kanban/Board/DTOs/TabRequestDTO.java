package com.projeto.backend.Kanban.Board.DTOs;

import com.projeto.backend.Kanban.Board.Enums.TabActionOnMove;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TabRequestDTO(
        @NotBlank String name,
        @NotBlank String color,
        TabActionOnMove actionOnMove,
        @NotNull Long groupId
) { }
