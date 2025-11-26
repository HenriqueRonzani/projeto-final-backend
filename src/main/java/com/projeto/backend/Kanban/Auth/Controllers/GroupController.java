package com.projeto.backend.Kanban.Auth.Controllers;

import com.projeto.backend.Kanban.Auth.DTOs.GroupQueryRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.GroupRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.GroupResponseDTO;
import com.projeto.backend.Kanban.Auth.DTOs.GroupUsersUpdateDTO;
import com.projeto.backend.Kanban.Auth.Services.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public GroupResponseDTO findById(@PathVariable("id") long id) {
        return this.groupService.getGroupById(id);
    }

    @GetMapping("/all")
    public List<GroupResponseDTO> getAllGroups(GroupQueryRequestDTO groupQueryRequestDTO) {
        return this.groupService.getAllGroups(groupQueryRequestDTO);
    }

    @PostMapping
    public ResponseEntity<GroupResponseDTO> createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        return ResponseEntity.status(201).body(groupService.createGroup(groupRequestDTO));
    }

    @PutMapping("/{id}")
    public GroupResponseDTO updateGroup(@RequestBody GroupRequestDTO groupRequestDTO, @PathVariable("id") long id) {
        return this.groupService.updateGroup(id, groupRequestDTO);
    }

    @PatchMapping("/{id}/users")
    public GroupResponseDTO setGroupUsers(@RequestBody GroupUsersUpdateDTO dto, @PathVariable("id") long id) {
        return this.groupService.setGroupUsers(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
