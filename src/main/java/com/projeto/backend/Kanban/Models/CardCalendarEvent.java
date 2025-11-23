package com.projeto.backend.Kanban.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "cards_events")
public class CardCalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "google_event_id")
    private String googleEventId;

    public CardCalendarEvent() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getGoogleEventId() {
        return googleEventId;
    }

    public void setGoogleEventId(String googleEventId) {
        this.googleEventId = googleEventId;
    }
}
