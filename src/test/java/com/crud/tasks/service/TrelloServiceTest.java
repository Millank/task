package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        // When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        // Then
        assertEquals(trelloBoards, result);
    }

    @Test
    public void shouldReturnTrelloCardAndCallEmailServiceSend() {
        // Given
        TrelloCardDto card = new TrelloCardDto("test_name", "test_description", "test_pos", "test_list_id");
        when(trelloClient.createNewCard(card)).thenReturn(new CreatedTrelloCardDto("1", "test_name", "https://example.com/cards"));
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(card);

        // Then
        assertEquals("1", result.getId());
        assertEquals("test_name", result.getName());
        assertEquals("https://example.com/cards", result.getShortUrl());

        Mail mail = new Mail(MailMessageType.NEW_CARD_MESSAGE, "admin@test.com", "Tasks: New Trello card",
                "New card \"test_name\" has been created on your Trello account");

        verify(emailService, times(1)).send(eq(mail));
        verify(emailService, times(1)).send(any());
    }

    @Test
    public void shouldReturnNullAndDoesntCallEmailServiceSend() {
        // Given
        TrelloCardDto card = new TrelloCardDto("test_name", "test_description", "test_pos", "test_list_id");
        when(trelloClient.createNewCard(card)).thenReturn(null);

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(card);

        // Then
        assertNull(result);
        verify(emailService, times(0)).send(any());
    }
}
