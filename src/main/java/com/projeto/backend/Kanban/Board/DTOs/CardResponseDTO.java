package com.projeto.backend.Kanban.Board.DTOs;

import com.projeto.backend.Kanban.Board.Enums.CardStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CardResponseDTO(
        @NotNull Long id,
        @NotBlank String title,
        @NotBlank String content,
        @NotNull CardStatus status,
        String start,
        String end,
        @NotNull Long creatorId,
        @NotNull Long tabId,
        List<Long> userIds
) {}
