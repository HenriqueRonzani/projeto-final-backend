package com.projeto.backend.Kanban.Integration.Google.Repositories;

import com.projeto.backend.Kanban.Models.OAuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthTokenRepository extends JpaRepository<OAuthToken, Long> {
    Optional<OAuthToken> findFirstByUserIdOrderByExpiresAtDesc(Long userId);
}
