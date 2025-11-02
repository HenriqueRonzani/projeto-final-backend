package com.projeto.backend.Kanban.Auth.Repositories;

import com.projeto.backend.Kanban.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
