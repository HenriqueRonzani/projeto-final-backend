package com.projeto.backend.Kanban.Board.Repositories;

import com.projeto.backend.Kanban.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
