package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloValidator.class);

    public List<TrelloBoard> validateTrelloBoards(List<TrelloBoard> trelloBoards) {
        LOGGER.info("Starting filtering boards...");
        List<TrelloBoard> filteredBoards = trelloBoards.stream()
                .filter(trelloBoard -> trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(toList());
        LOGGER.info("Boards have been filtered. Current list size: " + filteredBoards.size());

        return filteredBoards;
    }

    public void validateCard(final TrelloCard trelloCard) {
        if (trelloCard == null) {
            throw new IllegalArgumentException();
        }
        if (trelloCard.getName().equalsIgnoreCase("test")) {
            LOGGER.info("Someone is testing my application!");
        } else {
            LOGGER.info("Seems my application is used in proper way.");
        }
    }
}
