package com.core.gameservice.controllers;

import com.core.gameservice.entity.GameProvider;
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

   @GetMapping("/all")
    public ResponseEntity<List<String>> getAllProviders(){
        try {
            List<String> gameProviders =  gameProvidersService.getAllProviders();

            return new ResponseEntity<>(gameProviders, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGameProviders(@RequestBody GameProvider gameProvider){
        try {
            GameProvider savedGameProvider=gameProvidersService.createGameProvider(gameProvider);

            return new ResponseEntity<>(savedGameProvider, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  public ResponseEntity updateGameProviderStatus(Map<String,Boolean> gameStatusMap){
     // gameProvidersService.updateGameProviderStatus(gameStatusMap)

       return null;
   }
}
