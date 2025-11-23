package com.projeto.backend.Kanban.Integration.Google.DTOs.EventCrudDTOs;

import java.util.List;

public record CalendarEventRequestDTO(
        String summary,
        DateTime start,
        DateTime end,
        List<Attendee> attendees
) {
    public record DateTime(String dateTime, String timeZone) {}
    public record Attendee(String email) {}
}
