package com.projeto.backend.Kanban.Board.Services;

import com.projeto.backend.Kanban.Board.DTOs.CardRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.CardResponseDTO;
import com.projeto.backend.Kanban.Models.Card;
import com.projeto.backend.Kanban.Models.Tab;
import com.projeto.backend.Kanban.Models.User;
import com.projeto.backend.Kanban.Board.Repositories.CardRepository;
import com.projeto.backend.Kanban.Board.Repositories.TabRepository;
import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final TabRepository tabRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository, TabRepository tabRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.tabRepository = tabRepository;
    }

    // --------------------------
    // CREATE
    // --------------------------
    public CardResponseDTO create(CardRequestDTO dto) {
        Card card = new Card();
        updateCardFromDTO(card, dto);
        cardRepository.save(card);
        return toResponse(card);
    }

    // --------------------------
    // UPDATE
    // --------------------------
    public CardResponseDTO update(Long id, CardRequestDTO dto) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        updateCardFromDTO(card, dto);
        cardRepository.save(card);

        return toResponse(card);
    }

    // --------------------------
    // FIND BY ID
    // --------------------------
    public CardResponseDTO findById(Long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card não encontrado"));

        return toResponse(card);
    }

    // --------------------------
    // FIND ALL
    // --------------------------
    public List<CardResponseDTO> findAll() {
        return cardRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // --------------------------
    // DELETE
    // --------------------------
    public void delete(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new RuntimeException("Card não encontrado");
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

        card.setTitle(dto.getTitle());
        card.setContent(dto.getContent());
        card.setStatus(dto.getStatus());
        card.setStart(dto.getStart());
        card.setEnd(dto.getEnd());

        // Creator
        User creator = userRepository.findById(dto.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Criador não encontrado"));
        card.setCreator(creator);

        // Tab
        Tab tab = tabRepository.findById(dto.getTabId())
                .orElseThrow(() -> new RuntimeException("Aba não encontrada"));
        card.setTab(tab);

        // ManyToMany users
        if (dto.getUserIds() != null) {
            List<User> users = dto.getUserIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id)))
                    .collect(Collectors.toList());
            card.setUsers(users);
        }
    }

    // --------------------------
    // toResponse()
    // --------------------------
    private CardResponseDTO toResponse(Card card) {
        CardResponseDTO res = new CardResponseDTO();
        res.setId(card.getId());
        res.setTitle(card.getTitle());
        res.setContent(card.getContent());
        res.setStatus(card.getStatus());
        res.setStart(card.getStart());
        res.setEnd(card.getEnd());
        res.setCreatorId(card.getCreator().getId());
        res.setTabId(card.getTab().getId());

        if (card.getUsers() != null) {
            res.setUserIds(card.getUsers()
                    .stream()
                    .map(User::getId)
                    .toList());
        }

        return res;
    }
}