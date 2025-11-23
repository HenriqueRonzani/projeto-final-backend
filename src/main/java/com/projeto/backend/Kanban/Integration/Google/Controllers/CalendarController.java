package com.projeto.backend.Kanban.Integration.Google.Controllers;

import com.projeto.backend.Kanban.Integration.Google.Services.OAuthTokenService;
import com.projeto.backend.Kanban.Integration.Google.DTOs.ConsentResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private final OAuthTokenService oAuthTokenService;

    public CalendarController(OAuthTokenService oAuthTokenService) {
        this.oAuthTokenService = oAuthTokenService;
    }

    @PostMapping("/consent")
    public ConsentResponseDTO getConsentUrl() { return oAuthTokenService.makeConsentUrl(); }

    @GetMapping("/consent/callback")
    public ResponseEntity<String> consentCallback(@RequestParam String code, @RequestParam String state) {
        oAuthTokenService.consentCallback(code, state);
        return ResponseEntity.ok("Consent flow finished");
    }
}
