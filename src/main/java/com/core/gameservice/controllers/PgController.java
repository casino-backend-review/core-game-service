package com.core.gameservice.controllers;

import com.core.gameservice.dto.GameListResponse;
import com.core.gameservice.dto.PartnerLoginResponse;
import com.core.gameservice.dto.PgLoginRequest;
import com.core.gameservice.services.PgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/pg")
public class PgController {

    private final PgService pgService;

    @Autowired
    public PgController(PgService pgService) {
        this.pgService = pgService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PgLoginRequest pgLoginRequest) {
        try {
            PartnerLoginResponse response = pgService.login(pgLoginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Generic exception handling, could be more specific depending on your needs
            return ResponseEntity.internalServerError().body("An unexpected error occurred");
        }
    }

    @GetMapping("/games")
    public ResponseEntity<?> gameList() {
        try {
            GameListResponse response = (GameListResponse) pgService.gameList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred");
        }
    }
}
