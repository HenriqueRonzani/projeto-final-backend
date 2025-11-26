package com.projeto.backend.Kanban.Board.Controllers;

import com.projeto.backend.Kanban.Board.DTOs.TabQueryRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.TabRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.TabResponseDTO;
import com.projeto.backend.Kanban.Board.Services.TabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tabs")
public class TabController {

    private final TabService service;

    public TabController(TabService service) {
        this.service = service;
    }

    // POST
    @PostMapping
    public ResponseEntity<TabResponseDTO> create(@RequestBody TabRequestDTO dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    // PUT
    @PutMapping("/{id}")
    public TabResponseDTO update(@PathVariable Long id, @RequestBody TabRequestDTO dto) {
        return service.update(id, dto);
    }

    // GET ONE
    @GetMapping("/{id}")
    public TabResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    // GET ALL
    @GetMapping
    public List<TabResponseDTO> findAll(TabQueryRequestDTO filters) {
        return service.findAll(filters);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}