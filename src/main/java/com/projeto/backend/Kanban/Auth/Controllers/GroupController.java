package com.projeto.backend.Kanban.Auth.Controllers;

import com.projeto.backend.Kanban.Auth.DTOs.GroupQueryRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.GroupRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.GroupResponseDTO;
import com.projeto.backend.Kanban.Auth.Services.GroupService;
import com.projeto.backend.Kanban.Models.Group;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public GroupResponseDTO findById(@PathParam("id") long id) {
        return this.groupService.getGroupById(id);
    }

    @GetMapping("/all")
    public List<GroupResponseDTO> getAllGroups(GroupQueryRequestDTO groupQueryRequestDTO) {
        return this.groupService.getAllGroups(groupQueryRequestDTO);
    }

    @PostMapping("/")
    public GroupResponseDTO createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        return this.groupService.createGroup(groupRequestDTO);
    }

    @PutMapping("/{id}")
    public GroupResponseDTO updateGroup(@RequestBody GroupRequestDTO groupRequestDTO, @PathParam("id") long id) {
        return this.groupService.updateGroup(id, groupRequestDTO);
    }

    @PutMapping("/{id}/users")
    public GroupResponseDTO setGroupUsers(@RequestBody List<Long> userIds, @PathParam("id") long id) {
        return this.groupService.setGroupUsers(id, userIds);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathParam("id") long id) {
        this.groupService.deleteGroup(id);
    }
}
