package com.projeto.backend.Kanban.Integration.Google.DTOs.TokenDTOs;

public record OAuthTokenResponseDTO(
        String access_token,
        String refresh_token,
        Long expires_in,
        String scope,
        String token_type
) {}