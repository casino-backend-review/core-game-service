package com.core.gameservice.controllers;

import com.core.gameservice.dto.*;
import com.core.gameservice.exceptions.CustomException;
import com.core.gameservice.services.AgentGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent/game")
public class AgentGameController {

    private final AgentGameService agentGameService;

    @Autowired
    public AgentGameController(AgentGameService agentGameService) {
        this.agentGameService = agentGameService;
    }

    @PostMapping("/create")
    public ResponseEntity<List<AgentGameResponse>> createAgentGame(@RequestBody CreateAgentGameRequest request) {
        try {
            List<AgentGameResponse> response = agentGameService.createAgentGame(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<List<AgentGameResponse>> updateAgentGame(@RequestBody UpdateAgentGameRequest request) {
        try {
            List<AgentGameResponse> response = agentGameService.updateAgentGame(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<AgentGameResponse> getAgentGameDetails(@RequestParam String username, @RequestParam String productId) {
        try {
            GetAgentGameDetailsRequest request = new GetAgentGameDetailsRequest();
            request.setAgentId(username);
            request.setGameId(productId);
            AgentGameResponse response = agentGameService.getAgentGameDetails(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AgentGameResponse>> getAgentGames(@RequestParam String username) {
        try {
            List<AgentGameResponse> response = agentGameService.getAgentGame(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAgentGame(@RequestParam String username) {
        try {
            agentGameService.deleteAgentGame(username);
            return new ResponseEntity<>("Agent games deleted successfully", HttpStatus.OK);
        } catch (CustomException e) {
            return new ResponseEntity<>("Error deleting agent games", HttpStatus.BAD_REQUEST);
        }
    }
}
