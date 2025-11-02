package com.projeto.backend.Kanban.Auth.Services;

import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public User getUserById(int id) {
        return this.userRepository.findById((long) id).orElse(null);
    }

    public User updateUser(int id, User toUpdate) {
        User user = this.userRepository.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Usuario nao existente"));
        user.setName(toUpdate.getName());
        user.setEmail(toUpdate.getEmail());
        user.setPassword(passwordEncoder.encode(toUpdate.getPassword()));
        return this.userRepository.save(user);
    }

    public void deleteUser(int id) {
        this.userRepository.deleteById((long) id);
    }

}
