package com.projeto.backend.Kanban.Board.Controllers;

import com.projeto.backend.Kanban.Board.DTOs.CardQueryRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.CardRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.CardResponseDTO;
import com.projeto.backend.Kanban.Board.Services.CardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    // POST - criar
    @PostMapping
    public ResponseEntity<CardResponseDTO> create(@Valid @RequestBody CardRequestDTO dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    // PUT - atualizar
    @PutMapping("/{id}")
    public CardResponseDTO update(@Valid @PathVariable Long id, @RequestBody CardRequestDTO dto) {
        return service.update(id, dto);
    }

    // GET por ID
    @GetMapping("/{id}")
    public CardResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    // GET ALL
    @GetMapping
    public List<CardResponseDTO> findAll(@Valid CardQueryRequestDTO dto) {
        return service.findAll(dto);
    }

    @PatchMapping("/{id}/tabs/{tab_id}")
    public CardResponseDTO moveTab(@PathVariable Long id, @PathVariable Long tab_id) {
        return service.moveTab(id, tab_id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}