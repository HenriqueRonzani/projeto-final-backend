package com.projeto.backend.Kanban.Auth.Services;

import com.projeto.backend.Kanban.Auth.DTOs.UserQueryRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserRequestDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserResponseDTO;
import com.projeto.backend.Kanban.Auth.DTOs.UserUpdateDTO;
import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Auth.Specifications.UserSpecs;
import com.projeto.backend.Kanban.Models.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public User createUserEntity(UserRequestDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já está em uso.");
        }

        User user = new User(
                dto.name(),
                dto.email(),
                passwordEncoder.encode(dto.password())
        );
        return userRepository.save(user);
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {

        return toResponse(this.createUserEntity(dto));
    }

    public List<UserResponseDTO> getAllUsers(UserQueryRequestDTO filters) {
        return userRepository.findAll(UserSpecs.withFilters(filters))
                .stream().map(this::toResponse).toList();
    }

    public UserResponseDTO getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).map(this::toResponse).orElse(null);
    }

    public UserResponseDTO getUserById(long id) {
        return this.userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao existente"));
    }

    public UserResponseDTO updateUser(long id, UserUpdateDTO toUpdate) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario nao existente"));

        user.setName(toUpdate.name());
        user.setEmail(toUpdate.email());

        if  (toUpdate.password() != null) {
            user.setPassword(passwordEncoder.encode(toUpdate.password()));
        }
        return toResponse(this.userRepository.save(user));
    }

    public void notifyUser(User user, String subject, String text) {
        mailService.sendMail(user.getEmail(), subject, text);
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
