package com.core.gameservice.controllers;

import com.core.gameservice.dto.AgentGameResponse;
import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.exception.ApiResponseMessage;
import com.core.gameservice.exception.Error;
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

            return ResponseEntity.ok(ApiResponseMessage.<List<GameProvider>>builder().data(gameProviders).build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseMessage<GameProvider>> createGameProviders(@RequestBody GameProvider gameProvider){
        try {
            GameProvider savedGameProvider=gameProvidersService.createGameProvider(gameProvider);
            return ResponseEntity.ok(ApiResponseMessage.<GameProvider>builder().data(savedGameProvider).build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    private static ResponseEntity getFailureResponseEntity(ApiException exception) {
        HttpStatus status = exception.getStatus() != null ? exception.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage(null, Error.builder().code(exception.getErrorCode()).message(exception.getMessage()).build());
        return ResponseEntity.status(status).body(apiResponseMessage);
    }

}
