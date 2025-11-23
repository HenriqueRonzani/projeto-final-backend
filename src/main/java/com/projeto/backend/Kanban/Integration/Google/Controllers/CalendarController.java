package com.projeto.backend.Kanban.Integration.Google.Controllers;

import com.projeto.backend.Kanban.Integration.Google.Services.OAuthTokenService;
import com.projeto.backend.Kanban.Integration.Google.DTOs.ConsentResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private final OAuthTokenService calendarService;

    public CalendarController(OAuthTokenService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping("/consent")
    public ConsentResponseDTO getConsentUrl() { return calendarService.makeConsentUrl(); }

    @GetMapping("/consent/callback")
    public void consentCallback(@RequestParam String code, @RequestParam String state) {
        calendarService.consentCallback(code, state);
    }
}
