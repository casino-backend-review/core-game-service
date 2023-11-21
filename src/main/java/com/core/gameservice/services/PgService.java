package com.core.gameservice.services;

import com.core.gameservice.dto.PartnerLoginResponse;
import com.core.gameservice.dto.PgGameResponse;
import com.core.gameservice.dto.PgLoginRequest;
import com.core.gameservice.entity.AgentGame;
import com.core.gameservice.exceptions.BadRequestException;
import com.core.gameservice.exceptions.InternalErrorException;
import com.core.gameservice.repositories.AgentGameRepository;
import com.core.gameservice.repositories.GameProviderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PgService {

    private final AgentGameRepository agentGameRepository;
    private final GameProviderRepository gameProviderRepository;
    private final WalletServiceClient walletClient;
    private final String productId;
    private final String provider;
    private final String partnerBaseUrl;
    private final RestTemplate restTemplate;

    public PgService(AgentGameRepository agentGameRepository,
                     GameProviderRepository gameProviderRepository,
                     WalletServiceClient walletClient,
                     @Value("${partner.pg.productId}") String productId,
                     @Value("${partner.pg.provider}") String provider,
                     @Value("${partner.pg.baseUrl}") String partnerBaseUrl,
                     RestTemplate restTemplate) {
        this.agentGameRepository = agentGameRepository;
        this.gameProviderRepository = gameProviderRepository;
        this.walletClient = walletClient;
        this.productId = productId;
        this.provider = provider;
        this.partnerBaseUrl = partnerBaseUrl;
        this.restTemplate = restTemplate;
    }

    public PartnerLoginResponse login(PgLoginRequest pgLoginRequest) throws BadRequestException, InternalErrorException {
        // Logic to call WalletServiceClient and verify user

        Object user = null;
        boolean isActive = verifyAgentGame(this.productId, String.valueOf(user.getClass()), this.provider);
        if (!isActive) {
            throw new BadRequestException("Games inactive");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<PartnerLoginResponse> response = restTemplate.postForEntity(
                    partnerBaseUrl + "/api/pg/login",
                    pgLoginRequest,
                    PartnerLoginResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new InternalErrorException("Error fetching games");
            }
        } catch (HttpClientErrorException e) {
            throw new InternalErrorException("Error fetching games: " + e.getMessage());
        }
    }

    public boolean verifyAgentGame(String productId, String upline, String provider) {
        List<AgentGame> agentGames = agentGameRepository.findAllByUsernameAndStatusAndProductId(upline, "A", productId);
        // Additional logic as per the Go code
        return false; // Simplified for brevity
    }

    public List<PgGameResponse> gameList() throws InternalErrorException {
        try {
            ResponseEntity<List<PgGameResponse>> response = restTemplate.exchange(
                    partnerBaseUrl + "/api/pg/gameList",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<PgGameResponse>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new InternalErrorException("Error fetching games");
            }
        } catch (HttpClientErrorException e) {
            throw new InternalErrorException("Error fetching games: " + e.getMessage());
        }
    }


}
