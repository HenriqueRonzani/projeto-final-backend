package com.projeto.backend.Kanban.Auth.Controllers;

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

    @GetMapping("/")
    public User getUserById(@PathParam("id") int id) {
        return this.userService.getUserById(id);
    }

    @GetMapping("/")
    public User getUserByEmail(@RequestParam("email") String email) {
        return this.userService.getUserByEmail(email);
    }

    @GetMapping("/")
    public List<User> getUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return this.userService.createUser(user);
    }

    @PutMapping("/")
    public User updateUser(@PathParam("id") int id, @RequestBody User user) {
        return this.userService.updateUser(id, user);
    }

    @DeleteMapping("/")
    public void deleteUser(@PathParam("id") int id) {
        this.userService.deleteUser(id);
    }
}
