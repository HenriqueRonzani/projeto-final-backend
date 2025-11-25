package com.projeto.backend.Kanban.Auth.Board.Controllers;

import com.projeto.backend.Kanban.Auth.Board.DTOs.TabRequestDTO;
import com.projeto.backend.Kanban.Auth.Board.DTOs.TabResponseDTO;
import com.projeto.backend.Kanban.Auth.Board.Services.TabService;
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
    public TabResponseDTO create(@RequestBody TabRequestDTO dto) {
        return service.create(dto);
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
    public List<TabResponseDTO> findAll() {
        return service.findAll();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}