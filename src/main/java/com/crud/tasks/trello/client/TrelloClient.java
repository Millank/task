package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildTrelloBoardsURI(), TrelloBoardDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private URI buildTrelloBoardsURI() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint())
                   .path(trelloConfig.getTrelloBoardsPath())
                   .queryParam("key", trelloConfig.getTrelloAppKey())
                   .queryParam("token", trelloConfig.getTrelloToken())
                   .queryParam("fields", "name,id")
                   .queryParam("lists", "all")
                   .build().encode().toUri();
    }

    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        return restTemplate.postForObject(buildTrelloCardsURI(trelloCardDto), null, CreatedTrelloCardDto.class);
    }

    private URI buildTrelloCardsURI(TrelloCardDto trelloCardDto) {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint())
                   .path(trelloConfig.getTrelloCardsPath())
                   .queryParam("key", trelloConfig.getTrelloAppKey())
                   .queryParam("token", trelloConfig.getTrelloToken())
                   .queryParam("name", trelloCardDto.getName())
                   .queryParam("desc", trelloCardDto.getDescription())
                   .queryParam("pos", trelloCardDto.getPos())
                   .queryParam("idList", trelloCardDto.getListId())
                   .build().encode().toUri();
    }
}