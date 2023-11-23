package com.core.gameservice.controllers;

import com.core.gameservice.dto.GameListResponse;
import com.core.gameservice.dto.PartnerLoginResponse;
import com.core.gameservice.dto.PgLoginRequest;
import com.core.gameservice.services.PgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pg")
public class PgController {

    private final PgService pgService;
    private static final Logger logger = LoggerFactory.getLogger(PgController.class);

    public PgController(PgService pgService) {
        this.pgService = pgService;
    }

    @PostMapping("/login")
    public ResponseEntity<PartnerLoginResponse> login(@RequestBody PgLoginRequest pgLoginRequest) {
        try {
            PartnerLoginResponse response = pgService.login(pgLoginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new PartnerLoginResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/games")
    public ResponseEntity<GameListResponse> gameList() {
        try {
            GameListResponse response = pgService.gameList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching game list: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(new GameListResponse("Error", e.getMessage()));
        }
    }
}
