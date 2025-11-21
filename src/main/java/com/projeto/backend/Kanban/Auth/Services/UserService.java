package com.projeto.backend.Kanban.Auth.Services;

import com.projeto.backend.Kanban.Auth.DTOs.UserRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserResponseDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserUpdateDTO;
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

    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = new User(
                dto.name(),
                dto.email(),
                passwordEncoder.encode(dto.password())
        );

        return toResponse(userRepository.save(user));
    }

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponseDTO getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).map(this::toResponse).orElse(null);
    }

    public UserResponseDTO getUserById(long id) {
        return this.userRepository.findById(id).map(this::toResponse).orElse(null);
    }

    public UserResponseDTO updateUser(long id, UserUpdateDTO toUpdate) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario nao existente"));

        user.setName(toUpdate.name());
        user.setEmail(toUpdate.email());

        if  (toUpdate.password() != null) {
            user.setPassword(passwordEncoder.encode(toUpdate.password()));
        }
        return toResponse(this.userRepository.save(user));
    }

    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    private UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
