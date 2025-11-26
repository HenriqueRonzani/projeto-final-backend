package com.projeto.backend.Kanban.Auth.Services;

import com.projeto.backend.Kanban.Auth.DTOs.GroupQueryRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.GroupRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.GroupResponseDTO;
import com.projeto.backend.Kanban.Auth.DTOs.GroupUsersUpdateDTO;
import com.projeto.backend.Kanban.Auth.Repositories.GroupRepository;
import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Auth.Specifications.GroupSpecs;
import com.projeto.backend.Kanban.Models.Group;
import com.projeto.backend.Kanban.Models.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupResponseDTO setGroupUsers(Long groupId, GroupUsersUpdateDTO dto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group nao existente"));

        List<User> users = userRepository.findAllById(dto.userIds());

        group.setUsers(users);

        return toResponse(groupRepository.save(group));
    }

    public GroupService(GroupRepository groupRepository,  UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public GroupResponseDTO createGroup(GroupRequestDTO dto) {
        Group group = new Group(
                dto.name()
        );

        return toResponse(groupRepository.save(group));
    }

    public List<GroupResponseDTO> getAllGroups(GroupQueryRequestDTO filters) {
        return groupRepository.findAll(GroupSpecs.withFilters(filters))
                .stream().map(this::toResponse).toList();
    }

    public GroupResponseDTO getGroupById(Long id) {
        return toResponse(groupRepository.findByIdWithUsers(id)
                .orElseThrow(() -> new EntityNotFoundException("Group nao existente"))
        );
    }

    public GroupResponseDTO updateGroup(Long id, GroupRequestDTO dto) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group nao existente"));

        group.setName(dto.name());
        return toResponse(groupRepository.save(group));
    }

    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }

    private GroupResponseDTO toResponse(Group group) {
        return new GroupResponseDTO(
                group.getId(),
                group.getName(),
                group.getUsers()
                        .stream()
                        .map(User::getId)
                        .toList()
        );
    }
}
