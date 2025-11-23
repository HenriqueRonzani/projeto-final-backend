package com.projeto.backend.Kanban.Integration.Google.Services;

import com.projeto.backend.Kanban.Auth.Repositories.UserRepository;
import com.projeto.backend.Kanban.Integration.Google.DTOs.ConsentResponseDTO;
import com.projeto.backend.Kanban.Integration.Google.DTOs.OAuthTokenResponseDTO;
import com.projeto.backend.Kanban.Integration.Google.Repositories.OAuthTokenRepository;
import com.projeto.backend.Kanban.Models.OAuthToken;
import com.projeto.backend.Kanban.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Service
public class OAuthTokenService {

    @Autowired
    public Dotenv dotenv;
    private final String oAuthClientId;
    private final String oAuthClientSecret;
    private final WebClient webClient;
    private final String consentCallbackUri;
    private final UserRepository userRepository;
    private final OAuthTokenRepository oAuthTokenRepository;

    public OAuthTokenService(WebClient webClient, UserRepository userRepository, OAuthTokenRepository oAuthTokenRepository) {
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
                        "?client_id=" + this.oAuthClientId +
                        "&redirect_uri=" + this.consentCallbackUri +
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
        OAuthTokenResponseDTO tokenResponseDTO = getOAuthToken(code);

        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(tokenResponseDTO.access_token());
        oAuthToken.setRefreshToken(tokenResponseDTO.refresh_token());
        oAuthToken.setExpiresAt(Instant.now().plusSeconds(tokenResponseDTO.expires_in()));
        oAuthToken.setUser(user);

        this.oAuthTokenRepository.save(oAuthToken);
    }

    public OAuthTokenResponseDTO getOAuthToken(String code) {
        return webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .body(
                        BodyInserters.fromFormData("code", code)
                                .with("client_id", this.oAuthClientId)
                                .with("client_secret", this.oAuthClientSecret)
                                .with("redirect_uri", this.consentCallbackUri)
                                .with("grant_type", "authorization_code")
                )
                .retrieve()
                .bodyToMono(OAuthTokenResponseDTO.class)
                .block();
    }
}
