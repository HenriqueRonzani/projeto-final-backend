package com.projeto.backend.Kanban.Auth.Controllers;

import com.projeto.backend.Kanban.Auth.DTOs.UserRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserResponseDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserUpdateDTO;
import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Auth.Services.UserService;
import com.projeto.backend.Kanban.Models.User;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathParam("id") int id) {
        return this.userService.getUserById(id);
    }

    @GetMapping("/")
    public UserResponseDTO getUserByEmail(@RequestParam("email") String email) {
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping("/")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO requestDTO) {
        return this.userService.createUser(requestDTO);
    }

    @PutMapping("/")
    public UserResponseDTO updateUser(@PathParam("id") int id, @RequestBody UserUpdateDTO updateDTO) {
        return this.userService.updateUser(id, updateDTO);
    }

    @DeleteMapping("/")
    public void deleteUser(@PathParam("id") int id) {
        this.userService.deleteUser(id);
    }
}
