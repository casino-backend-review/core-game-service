package com.core.gameservice.controllers;

import com.core.gameservice.dto.AgentGameResponse;
import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.exception.ApiResponseMessage;
import com.core.gameservice.services.GameProvidersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game/providers")
@AllArgsConstructor
public class GameProvidersController {

   private GameProvidersService gameProvidersService;

   @GetMapping("/get-all")
    public ResponseEntity<ApiResponseMessage<List<GameProvider>>> getAllProviders(){
        try {
            List<GameProvider> gameProviders = gameProvidersService.getAllProviders();

            return ResponseEntity.ok(ApiResponseMessage.<List<GameProvider>>builder().data(gameProviders).code(ApiResponseMessage.OK).type("ok").build());
        }catch (Exception exception) {
                ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR, exception.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseMessage);
            }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseMessage<GameProvider>> createGameProviders(@RequestBody GameProvider gameProvider){
        try {
            GameProvider savedGameProvider=gameProvidersService.createGameProvider(gameProvider);
            return ResponseEntity.ok(ApiResponseMessage.<GameProvider>builder().data(savedGameProvider).code(ApiResponseMessage.OK).type("ok").build());
        }catch (Exception exception) {
            ApiResponseMessage apiResponseMessage = new ApiResponseMessage(ApiResponseMessage.ERROR, exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponseMessage);
        }
    }

}
