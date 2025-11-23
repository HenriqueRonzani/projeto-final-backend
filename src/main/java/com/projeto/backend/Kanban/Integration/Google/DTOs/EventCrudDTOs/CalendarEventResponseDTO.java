package com.projeto.backend.Kanban.Integration.Google.DTOs.EventCrudDTOs;

import java.util.List;

public record CalendarEventResponseDTO(
        String id,
        String status,
        String htmlLink,
        String summary,
        String description,
        EventDateTime start,
        EventDateTime end,
        List<Attendee> attendees,
        Creator creator,
        Organizer organizer
) {

    public record EventDateTime(
            String dateTime,
            String timeZone
    ) {}

    public record Attendee(
            String email
    ) {}

    public record Creator(
            String email,
            String displayName
    ) {}

    public record Organizer(
            String email,
            String displayName
    ) {}
}
