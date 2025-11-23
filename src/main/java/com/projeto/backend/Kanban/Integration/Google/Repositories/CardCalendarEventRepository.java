package com.projeto.backend.Kanban.Integration.Google.Repositories;

import com.projeto.backend.Kanban.Models.CardCalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCalendarEventRepository extends JpaRepository<CardCalendarEvent, Long> {
}
