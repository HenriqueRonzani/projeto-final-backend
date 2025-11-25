package com.projeto.backend.Kanban.Board.Services;

import com.projeto.backend.Kanban.Board.DTOs.TabRequestDTO;
import com.projeto.backend.Kanban.Board.DTOs.TabResponseDTO;
import com.projeto.backend.Kanban.Models.Group;
import com.projeto.backend.Kanban.Models.Tab;
import com.projeto.backend.Kanban.Auth.Repositories.GroupRepository;
import com.projeto.backend.Kanban.Board.Repositories.TabRepository;
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
                .orElseThrow(() -> new RuntimeException("Tab não encontrada"));

        updateTabFromDTO(tab, dto);
        tabRepository.save(tab);
        return toResponse(tab);
    }

    // FIND BY ID
    public TabResponseDTO findById(Long id) {
        Tab tab = tabRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tab não encontrada"));
        return toResponse(tab);
    }

    // FIND ALL
    public List<TabResponseDTO> findAll() {
        return tabRepository.findAll()
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
        tab.setName(dto.getName());
        tab.setColor(dto.getColor());
        tab.setActionOnMove(dto.getActionOnMove());

        // Relacionamento ManyToOne com Group
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado"));
        tab.setGroup(group);
    }

    // -----------------------
    // toResponse()
    // -----------------------
    private TabResponseDTO toResponse(Tab tab) {
        TabResponseDTO res = new TabResponseDTO();

        res.setId(tab.getId());
        res.setName(tab.getName());
        res.setColor(tab.getColor());
        res.setActionOnMove(tab.getActionOnMove());
        res.setGroupId(tab.getGroup().getId());

        return res;
    }
}