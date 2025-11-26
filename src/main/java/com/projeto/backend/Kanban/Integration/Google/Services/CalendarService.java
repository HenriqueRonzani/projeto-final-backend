package com.projeto.backend.Kanban.Integration.Google.Services;

import com.projeto.backend.Kanban.Integration.Google.DTOs.EventCrudDTOs.CalendarEventRequestDTO;
import com.projeto.backend.Kanban.Integration.Google.DTOs.EventCrudDTOs.CalendarEventResponseDTO;
import com.projeto.backend.Kanban.Integration.Google.Repositories.CardCalendarEventRepository;
import com.projeto.backend.Kanban.Models.Card;
import com.projeto.backend.Kanban.Models.CardCalendarEvent;
import com.projeto.backend.Kanban.Models.OAuthToken;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CalendarService {
    private final WebClient webClient;
    private final OAuthTokenService oAuthTokenService;
    private final CardCalendarEventRepository cardCalendarEventRepository;

    public CalendarService(WebClient webClient, OAuthTokenService oAuthTokenService, CardCalendarEventRepository cardCalendarEventRepository) {
        this.webClient = webClient;
        this.oAuthTokenService = oAuthTokenService;
        this.cardCalendarEventRepository = cardCalendarEventRepository;
    }

    private CalendarEventResponseDTO createGoogleEvent(CalendarEventRequestDTO body) {
        OAuthToken token = oAuthTokenService.getValidOAuthToken();
        return webClient.post()
                .uri("https://www.googleapis.com/calendar/v3/calendars/primary/events")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(CalendarEventResponseDTO.class)
                .block();
    }

    private void updateGoogleEvent(CardCalendarEvent event, CalendarEventRequestDTO body) {
        OAuthToken token = oAuthTokenService.getValidOAuthToken(event.getCard().getCreator().getId());
        webClient.put()
                .uri("https://www.googleapis.com/calendar/v3/calendars/primary/events/" + event.getGoogleEventId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(CalendarEventResponseDTO.class)
                .block();
    }

    private void deleteGoogleEvent(CardCalendarEvent event) {
        OAuthToken token = oAuthTokenService.getValidOAuthToken(event.getCard().getCreator().getId());
        webClient.delete()
                .uri("https://www.googleapis.com/calendar/v3/calendars/primary/events/" + event.getGoogleEventId())
                .header("Authorization", "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void createEvent(Card card) {
        CalendarEventResponseDTO responseDTO = createGoogleEvent(cardToDTO(card));

        CardCalendarEvent event = new CardCalendarEvent();
        event.setCard(card);
        event.setGoogleEventId(responseDTO.id());
        cardCalendarEventRepository.save(event);
    }

    public void updateEvent(Card card) {
        CardCalendarEvent event = card.getCardCalendarEvent();
        updateGoogleEvent(event, cardToDTO(card));
    }

    public void deleteEvent(Card card) {
        CardCalendarEvent event = card.getCardCalendarEvent();
        deleteGoogleEvent(event);
        cardCalendarEventRepository.delete(event);
    }

    private CalendarEventRequestDTO cardToDTO(Card card) {
        // TODO: Add others fields when Kanban Crud is done
        return new CalendarEventRequestDTO(
                card.getTitle(),
                card.getContent(),
                new CalendarEventRequestDTO.DateTime(card.getStart(), "America/Sao_Paulo"),
                new CalendarEventRequestDTO.DateTime(card.getEnd(),  "America/Sao_Paulo"),
                card.getUsers().stream()
                        .map(user -> new CalendarEventRequestDTO.Attendee(user.getEmail()))
                        .toList()
        );
    }
}
