package com.projeto.backend.Kanban.Integration.Google.DTOs.EventCrudDTOs;

import java.util.List;

public record CalendarEventRequestDTO(
        String summary,
        String start,
        String end,
        List<Attendee> attendees
) {
    public record Attendee(String email) {}
}
