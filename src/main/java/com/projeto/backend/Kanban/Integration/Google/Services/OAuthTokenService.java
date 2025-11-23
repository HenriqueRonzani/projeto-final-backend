package com.projeto.backend.Kanban.Integration.Google.Services;

import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Integration.Google.DTOs.ConsentResponseDTO;
import com.projeto.backend.Kanban.Integration.Google.DTOs.TokenDTOs.OAuthRefreshTokenResponseDTO;
import com.projeto.backend.Kanban.Integration.Google.DTOs.TokenDTOs.OAuthTokenResponseDTO;
import com.projeto.backend.Kanban.Integration.Google.Repositories.OAuthTokenRepository;
import com.projeto.backend.Kanban.Models.OAuthToken;
import com.projeto.backend.Kanban.Models.User;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
public class OAuthTokenService {

    private final String oAuthClientId;
    private final String oAuthClientSecret;
    private final WebClient webClient;
    private final String consentCallbackUri;
    private final UserRepository userRepository;
    private final OAuthTokenRepository oAuthTokenRepository;

    public OAuthTokenService(WebClient webClient, UserRepository userRepository, OAuthTokenRepository oAuthTokenRepository, Dotenv dotenv) {
        this.webClient = webClient;
        this.userRepository = userRepository;
        this.oAuthTokenRepository = oAuthTokenRepository;
        this.oAuthClientId = dotenv.get("OAUTH_CLIENT_ID");
        this.oAuthClientSecret = dotenv.get("OAUTH_CLIENT_SECRET");
        this.consentCallbackUri = dotenv.get("BACKEND_URL") + "/calendar/consent/callback";
    }

    public ConsentResponseDTO makeConsentUrl() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) auth.getPrincipal();

        return new ConsentResponseDTO(
                "https://accounts.google.com/o/oauth2/v2/auth" +
                        "?client_id=" + oAuthClientId +
                        "&redirect_uri=" + consentCallbackUri +
                        "&response_type=code" +
                        "&scope=https://www.googleapis.com/auth/calendar" +
                        "&access_type=offline" +
                        "&prompt=consent" +
                        "&state=" + userId
        );
    }

    public void consentCallback(String code,  String state) {
        Long userId = Long.parseLong(state);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        OAuthTokenResponseDTO tokenResponseDTO = getFirstOAuthToken(code);

        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(tokenResponseDTO.access_token());
        oAuthToken.setRefreshToken(tokenResponseDTO.refresh_token());
        oAuthToken.setExpiresAt(Instant.now().plusSeconds(tokenResponseDTO.expires_in()));
        oAuthToken.setUser(user);

        oAuthTokenRepository.save(oAuthToken);
    }

    private OAuthTokenResponseDTO getFirstOAuthToken(String code) {
        return webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .body(
                        BodyInserters.fromFormData("code", code)
                                .with("client_id", oAuthClientId)
                                .with("client_secret", oAuthClientSecret)
                                .with("redirect_uri", consentCallbackUri)
                                .with("grant_type", "authorization_code")
                )
                .retrieve()
                .bodyToMono(OAuthTokenResponseDTO.class)
                .block();
    }

    private OAuthToken refreshToken(OAuthToken token) {
        OAuthRefreshTokenResponseDTO tokenResponseDTO = webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(
                        BodyInserters.fromFormData("client_id", oAuthClientId)
                                .with("client_secret", oAuthClientSecret)
                                .with("refresh_token", token.getRefreshToken())
                                .with("grant_type", "refresh_token")
                )
                .retrieve()
                .bodyToMono(OAuthRefreshTokenResponseDTO.class)
                .block();

        token.setAccessToken(tokenResponseDTO.access_token());
        token.setExpiresAt(Instant.now().plusSeconds(tokenResponseDTO.expires_in()));
        oAuthTokenRepository.save(token);
        return token;
    }

    public OAuthToken getValidOAuthToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) auth.getPrincipal();

        OAuthToken oAuthToken = oAuthTokenRepository.findFirstByUserIdOrderByExpiresAtDesc(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (oAuthToken.getExpiresAt().isBefore(Instant.now())) {
            return refreshToken(oAuthToken);
        }

        return oAuthToken;
    }

    public OAuthToken getValidOAuthToken(Long userId) {
        OAuthToken oAuthToken = oAuthTokenRepository.findFirstByUserIdOrderByExpiresAtDesc(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (oAuthToken.getExpiresAt().isBefore(Instant.now())) {
            return refreshToken(oAuthToken);
        }

        return oAuthToken;
    }
}
