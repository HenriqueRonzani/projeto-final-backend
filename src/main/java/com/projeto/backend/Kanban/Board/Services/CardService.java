package com.projeto.backend.Kanban.Board.Services;

import com.projeto.backend.Kanban.Board.DTOs.CardQueryRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.CardRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.CardResponseDTO;
import com.projeto.backend.Kanban.Board.Enums.CardStatus;
import com.projeto.backend.Kanban.Board.Enums.TabActionOnMove;
import com.projeto.backend.Kanban.Board.Specifications.CardSpecs;
import com.projeto.backend.Kanban.Integration.Google.Services.CalendarService;
import com.projeto.backend.Kanban.Models.Card;
import com.projeto.backend.Kanban.Models.Tab;
import com.projeto.backend.Kanban.Models.User;
import com.projeto.backend.Kanban.Board.Repositories.CardRepository;
import com.projeto.backend.Kanban.Board.Repositories.TabRepository;
import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final TabRepository tabRepository;
    private final CalendarService calendarService;

    public CardService(CardRepository cardRepository, UserRepository userRepository, TabRepository tabRepository, CalendarService calendarService) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.tabRepository = tabRepository;
        this.calendarService = calendarService;
    }

    // --------------------------
    // CREATE
    // --------------------------
    public CardResponseDTO create(CardRequestDTO dto) {
        Card card = new Card();
        updateCardFromDTO(card, dto);
        cardRepository.save(card);
        if (dto.createEvent() != null && dto.createEvent()) {
            calendarService.createEvent(card);
        }
        return toResponse(card);
    }

    // --------------------------
    // UPDATE
    // --------------------------
    public CardResponseDTO update(Long id, CardRequestDTO dto) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card não encontrado"));

        updateCardFromDTO(card, dto);
        cardRepository.save(card);
        if (card.getCardCalendarEvent() != null) {
            calendarService.updateEvent(card);
        }

        return toResponse(card);
    }

    // --------------------------
    // FIND BY ID
    // --------------------------
    public CardResponseDTO findById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card não encontrado"));

        return toResponse(card);
    }

    // --------------------------
    // FIND ALL
    // --------------------------
    public List<CardResponseDTO> findAll(CardQueryRequestDTO filters) {
        return cardRepository.findAll(CardSpecs.withFilters(filters)).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Move tab
    public CardResponseDTO moveTab(Long cardId, Long tabId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new EntityNotFoundException("Card não encontrado"));

        Tab tab = tabRepository.findById(tabId)
                .orElseThrow(() -> new EntityNotFoundException("Tab nao encontrada"));

        card.setTab(tab);
        card.setStatus(getStatusForAction(tab.getActionOnMove()));
        cardRepository.save(card);
        return toResponse(card);
    }

    // --------------------------
    // DELETE
    // --------------------------
    public void delete(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card não encontrado"));
        if (card.getCardCalendarEvent() != null) {
            calendarService.deleteEvent(card);
        }
        cardRepository.deleteById(id);
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public TabRepository getTabRepository() {
        return tabRepository;
    }

    // ------------------------------------------------------
    // MÉTODO AUXILIAR PARA PREENCHER O CARD COM BASE NO DTO
    // ------------------------------------------------------
    private void updateCardFromDTO(Card card, CardRequestDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) auth.getPrincipal();

        card.setTitle(dto.title());
        card.setContent(dto.content());
        card.setStatus(dto.status());
        card.setStart(dto.start());
        card.setEnd(dto.end());

        // Creator
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Criador não encontrado"));
        card.setCreator(creator);

        // Tab
        Tab tab = tabRepository.findById(dto.tabId())
                .orElseThrow(() -> new EntityNotFoundException("Aba não encontrada"));
        card.setTab(tab);

        // ManyToMany users
        List<User> users = dto.userIds().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id)))
                .collect(Collectors.toList());
        card.setUsers(users);
    }

    private static final Map<TabActionOnMove, CardStatus> ACTION_TO_STATUS = Map.ofEntries(
            Map.entry(TabActionOnMove.START, CardStatus.IN_PROGRESS),
            Map.entry(TabActionOnMove.WAIT, CardStatus.WAITING),
            Map.entry(TabActionOnMove.COMPLETE, CardStatus.FINISHED),
            Map.entry(TabActionOnMove.CANCEL, CardStatus.CANCELED),
            Map.entry(TabActionOnMove.PENDING, CardStatus.PENDING),
            Map.entry(TabActionOnMove.NOT_STARTED, CardStatus.NOT_STARTED)
    );

    private CardStatus getStatusForAction(TabActionOnMove action) {
        return ACTION_TO_STATUS.get(action);
    }

    // --------------------------
    // toResponse()
    // --------------------------
    private CardResponseDTO toResponse(Card card) {
        List<Long> userIds = card.getUsers() != null
                ? card.getUsers().stream().map(User::getId).toList()
                : List.of();

        return new CardResponseDTO(
                card.getId(),
                card.getTitle(),
                card.getContent(),
                card.getStatus(),
                card.getStart(),
                card.getEnd(),
                card.getCreator().getId(),
                card.getTab().getId(),
                userIds
        );
    }
}