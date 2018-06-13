package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class TrelloValidatorTest {
    private static Logger logger;

    private TrelloValidator validator;

    @BeforeClass
    public static void initClass() {
        mockStatic(LoggerFactory.class);
        logger = mock(Logger.class);
        when(LoggerFactory.getLogger(any(Class.class))).thenReturn(logger);
    }

    @Before
    public void initTestUnit() {
        reset(logger);
        validator = new TrelloValidator();
    }

    @Test
    public void testValidateTrelloBoardsWithoutTest() {
        // Given
        TrelloBoard board1 = new TrelloBoard("1", "name1", Arrays.asList(new TrelloList("1", "name1", false)));
        TrelloBoard board2 = new TrelloBoard("2", "name2", Arrays.asList(new TrelloList("2", "name2", true)));
        List<TrelloBoard> boards = Arrays.asList(board1, board2);

        // When
        List<TrelloBoard> result = validator.validateTrelloBoards(boards);

        // Then
        assertEquals(Collections.emptyList(), result);
        InOrder orderVerifier = inOrder(logger);
        orderVerifier.verify(logger, times(1)).info("Starting filtering boards...");
        orderVerifier.verify(logger, times(1)).info("Boards have been filtered. Current list size: 0");
    }

    @Test
    public void testValidateTrelloBoardsWithTest() {
        // Given
        TrelloBoard board1 = new TrelloBoard("1", "TeSt", Arrays.asList(new TrelloList("1", "name1", false)));
        TrelloBoard board2 = new TrelloBoard("2", "name2", Arrays.asList(new TrelloList("2", "name2", true)));
        List<TrelloBoard> boards = Arrays.asList(board1, board2);

        // When
        List<TrelloBoard> result = validator.validateTrelloBoards(boards);

        // Then
        assertEquals(Arrays.asList(board1), result);
        InOrder orderVerifier = inOrder(logger);
        orderVerifier.verify(logger, times(1)).info("Starting filtering boards...");
        orderVerifier.verify(logger, times(1)).info("Boards have been filtered. Current list size: 1");
    }

    @Test
    public void testValidateCardWithTestName() {
        // Given
        TrelloCard card = new TrelloCard("TeSt", "desc", "pos", "listId");
        // When
        validator.validateCard(card);
        // Then
        verify(logger, times(1)).info("Someone is testing my application!");
    }

    @Test
    public void testValidateCardWithoutTestName() {
        // Given
        TrelloCard card = new TrelloCard("name", "desc", "pos", "listId");
        // When
        validator.validateCard(card);
        // Then
        verify(logger, times(1)).info("Seems my application is used in proper way.");
    }
}
