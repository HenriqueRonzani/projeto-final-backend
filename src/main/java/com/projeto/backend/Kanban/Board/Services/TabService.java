package com.projeto.backend.Kanban.Board.Services;

import com.projeto.backend.Kanban.Board.DTOs.TabQueryRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.TabRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.TabResponseDTO;
import com.projeto.backend.Kanban.Board.Specifications.TabSpecs;
import com.projeto.backend.Kanban.Models.Group;
import com.projeto.backend.Kanban.Models.Tab;
import com.projeto.backend.Kanban.Auth.Repositories.GroupRepository;
import com.projeto.backend.Kanban.Board.Repositories.TabRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TabService {

    private final TabRepository tabRepository;
    private final GroupRepository groupRepository;

    public TabService(TabRepository tabRepository, GroupRepository groupRepository) {
        this.tabRepository = tabRepository;
        this.groupRepository = groupRepository;
    }

    // CREATE
    public TabResponseDTO create(TabRequestDTO dto) {
        Tab tab = new Tab();
        updateTabFromDTO(tab, dto);
        tabRepository.save(tab);
        return toResponse(tab);
    }

    // UPDATE
    public TabResponseDTO update(Long id, TabRequestDTO dto) {
        Tab tab = tabRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tab não encontrada"));

        updateTabFromDTO(tab, dto);
        tabRepository.save(tab);
        return toResponse(tab);
    }

    // FIND BY ID
    public TabResponseDTO findById(Long id) {
        Tab tab = tabRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tab não encontrada"));
        return toResponse(tab);
    }

    // FIND ALL
    public List<TabResponseDTO> findAll(TabQueryRequestDTO filters) {
        return tabRepository.findAll(TabSpecs.withFilters(filters))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // DELETE
    public void delete(Long id) {
        if (!tabRepository.existsById(id)) {
            throw new RuntimeException("Tab não encontrada");
        }
        tabRepository.deleteById(id);
    }

    // -----------------------
    // updateTabFromDTO()
    // -----------------------
    private void updateTabFromDTO(Tab tab, TabRequestDTO dto) {
        tab.setName(dto.name());
        tab.setColor(dto.color());
        tab.setActionOnMove(dto.actionOnMove());

        // Relacionamento ManyToOne com Group
        Group group = groupRepository.findById(dto.groupId())
                .orElseThrow(() -> new EntityNotFoundException("Grupo não encontrado"));
        tab.setGroup(group);
    }

    // -----------------------
    // toResponse()
    // -----------------------
    private TabResponseDTO toResponse(Tab tab) {
        return new TabResponseDTO(
                tab.getId(),
                tab.getName(),
                tab.getColor(),
                tab.getActionOnMove(),
                tab.getGroup() != null ? tab.getGroup().getId() : null
        );
    }
}