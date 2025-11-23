package com.projeto.backend.Kanban.Integration.Google.DTOs.TokenDTOs;

public record OAuthRefreshTokenResponseDTO(
        String access_token,
        Long expires_in,
        String scope,
        String token_type
) {}
