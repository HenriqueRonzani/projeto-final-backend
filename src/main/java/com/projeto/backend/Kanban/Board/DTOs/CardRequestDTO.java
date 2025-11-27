package com.projeto.backend.Kanban.Board.DTOs;
import com.projeto.backend.Kanban.Board.Enums.CardStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CardRequestDTO(
        @NotBlank
        String title,

        @NotBlank
        String content,

        @NotNull
        CardStatus status,

        @NotBlank
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?(?:Z|[+-]\\d{2}:\\d{2})$",
                message = "start inválido, use formato RFC3339"
        )
        String start,

        @NotBlank
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?(?:Z|[+-]\\d{2}:\\d{2})$",
                message = "end inválido, use formato RFC3339"
        )
        String end,

        @NotNull
        Long tabId,

        @NotNull
        @Size(min = 1, message = "deve ter ao menos 1 usuário")
        List<Long> userIds,

        @Nullable
        Boolean createEvent
) {}