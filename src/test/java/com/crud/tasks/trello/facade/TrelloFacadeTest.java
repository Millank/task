package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @After
    public void mockReset() {
        Mockito.reset(trelloService);
        Mockito.reset(trelloValidator);
        Mockito.reset(trelloMapper);
    }

    @Test
    public void shouldFetchEmptyList() {
        // Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));
        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));
        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        // When
        List<TrelloBoardDto> trelloBoardDtos =trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));
        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));
        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "my_task", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCallValidatorAndReturnCardDto() {
        // Given
        TrelloCardDto card = new TrelloCardDto("name","test_desc","test_pos","test_list_id");
        CreatedTrelloCardDto cardDto = new CreatedTrelloCardDto("1", "name", "http://test.cards/1");
        TrelloCard mappedTrelloCard = new TrelloCard("name","test_desc","test_pos","test_list_id");

        when(trelloService.createTrelloCard(card)).thenReturn(cardDto);
        when(trelloMapper.mapToCard(card)).thenReturn(mappedTrelloCard);
        when(trelloMapper.mapToCardDto(mappedTrelloCard)).thenReturn(card);

        // When
        CreatedTrelloCardDto result = trelloFacade.createCard(card);

        // Then
        assertEquals(cardDto, result);
        verify(trelloValidator, times(1)).validateCard(mappedTrelloCard);
    }

    @Test
    public void shouldCallValidatorAndReturnCardDtoForTestName() {
        // Given
        TrelloCardDto card = new TrelloCardDto("test", "test_desc2", "test_pos2", "test_list_id2");
        CreatedTrelloCardDto cardDto = new CreatedTrelloCardDto("2", "test", "http://test.cards/2");
        TrelloCard mappedTrelloCard = new TrelloCard("test", "test_desc2", "test_pos2", "test_list_id2");

        when(trelloService.createTrelloCard(card)).thenReturn(cardDto);
        when(trelloMapper.mapToCard(card)).thenReturn(mappedTrelloCard);
        when(trelloMapper.mapToCardDto(mappedTrelloCard)).thenReturn(card);

        // When
        CreatedTrelloCardDto result = trelloFacade.createCard(card);

        // Then
        assertEquals(cardDto, result);
        verify(trelloValidator, times(1)).validateCard(mappedTrelloCard);
    }
}
