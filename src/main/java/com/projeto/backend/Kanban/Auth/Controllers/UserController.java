package com.projeto.backend.Kanban.Auth.Controllers;

import com.projeto.backend.Kanban.Auth.DTOs.UserQueryRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserResponseDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserUpdateDTO;
import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Auth.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable("id") long id) {
        return this.userService.getUserById(id);
    }

    @GetMapping
    public UserResponseDTO getUserByEmail(@RequestParam("email") String email) {
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getUsers(@Valid UserQueryRequestDTO userQueryRequestDTO) {
        return this.userService.getAllUsers(userQueryRequestDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.status(201).body(userService.createUser(requestDTO));
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateUser(@Valid @PathVariable("id") long id, @RequestBody UserUpdateDTO updateDTO) {
        return this.userService.updateUser(id, updateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }}
