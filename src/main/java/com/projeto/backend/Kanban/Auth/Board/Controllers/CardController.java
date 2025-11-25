package com.projeto.backend.Kanban.Auth.Board.Controllers;

import com.projeto.backend.Kanban.Auth.Board.DTOs.CardRequestDTO;
import com.projeto.backend.Kanban.Auth.Board.DTOs.CardResponseDTO;
import com.projeto.backend.Kanban.Auth.Board.Services.CardService;
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
    public CardResponseDTO create(@RequestBody CardRequestDTO dto) {
        return service.create(dto);
    }

    // PUT - atualizar
    @PutMapping("/{id}")
    public CardResponseDTO update(@PathVariable Long id, @RequestBody CardRequestDTO dto) {
        return service.update(id, dto);
    }

    // GET por ID
    @GetMapping("/{id}")
    public CardResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    // GET ALL
    @GetMapping
    public List<CardResponseDTO> findAll() {
        return service.findAll();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}