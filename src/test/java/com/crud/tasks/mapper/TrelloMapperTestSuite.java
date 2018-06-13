package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

// Assumed the most generic related fields mapping, i.e.:
// type.primitive == typeDto.primitive,
// type.string.equals(typeDto.string) including null values,
// (type)null <-> (typDto)null,

public class TrelloMapperTestSuite {
    private static final List<TrelloListDto> EMPTY_LIST_TRELLO_LIST_DTO = Collections.emptyList();
    private static final List<TrelloList> EMPTY_LIST_TRELLO_LIST = Collections.emptyList();
    private static final List<TrelloBoardDto> EMPTY_LIST_TRELLO_BOARD_DTO = Collections.emptyList();
    private static final List<TrelloBoard> EMPTY_LIST_TRELLO_BOARD = Collections.emptyList();

    private static List<TrelloListDto> trelloListDtoList;
    private static List<TrelloList> trelloListList;
    private static List<TrelloBoardDto> trelloBoardDtoList;
    private static List<TrelloBoard> trelloBoardList;

    private TrelloMapper trelloMapper;

    @Before
    public void init() {
        trelloMapper = new TrelloMapper();
    }

    @BeforeClass
    public static void prepareTrelloListsMappedPairs() {
        List<TrelloListDto> dtoLists = new ArrayList<>();
        List<TrelloList> domainLists = new ArrayList<>();
        dtoLists.add(new TrelloListDto("Id1", "Name1", false));
        domainLists.add(new TrelloList("Id1", "Name1", false));
        dtoLists.add(new TrelloListDto("Id2", "Name2", true));
        domainLists.add(new TrelloList("Id2", "Name2", true));
        dtoLists.add(new TrelloListDto(null, null, false));
        domainLists.add(new TrelloList(null, null, false));
        dtoLists.add(new TrelloListDto("", "", false));
        domainLists.add(new TrelloList("", "", false));

        dtoLists.add(null);
        domainLists.add(null);

        trelloListDtoList = Collections.unmodifiableList(dtoLists);
        trelloListList = Collections.unmodifiableList(domainLists);
    }

    @BeforeClass
    public static void prepareTrelloBoardsMappedPairs() {
        List<TrelloBoardDto> boardDtoList = new ArrayList<>();
        List<TrelloBoard> boardList = new ArrayList<>();
        boardDtoList.add(new TrelloBoardDto("Id1", "Name1", trelloListDtoList));
        boardList.add(new TrelloBoard("Id1", "Name1", trelloListList));
        boardDtoList.add(new TrelloBoardDto("Id2", "Name2", EMPTY_LIST_TRELLO_LIST_DTO));
        boardList.add(new TrelloBoard("Id2", "Name2", EMPTY_LIST_TRELLO_LIST));
        boardDtoList.add(new TrelloBoardDto("Id3", "Name3", null));
        boardList.add(new TrelloBoard("Id3", "Name3", null));
        boardDtoList.add(new TrelloBoardDto(null, null, null));
        boardList.add(new TrelloBoard(null, null, null));
        boardDtoList.add(new TrelloBoardDto("", "", EMPTY_LIST_TRELLO_LIST_DTO));
        boardList.add(new TrelloBoard("", "", EMPTY_LIST_TRELLO_LIST));

        boardDtoList.add(null);
        boardList.add(null);

        trelloBoardDtoList = Collections.unmodifiableList(boardDtoList);
        trelloBoardList = Collections.unmodifiableList(boardList);
    }

    @Test
    public void testMapToList() {
        // Given
        List<TrelloListDto> givenList = trelloListDtoList;
        List<TrelloList> expectedList = trelloListList;

        // When
        List<TrelloList> mapResult = trelloMapper.mapToList(givenList);

        // Then
        assertEquals(expectedList, mapResult);
    }

    @Test
    public void testMapToListNullOrEmpty() {
        // Given & When & Then
        assertEquals(EMPTY_LIST_TRELLO_LIST, trelloMapper.mapToList(EMPTY_LIST_TRELLO_LIST_DTO));
        assertNull(trelloMapper.mapToList(null));
    }

    @Test
    public void testMapToListDto() {
        // Given
        List<TrelloList> givenList = trelloListList;
        List<TrelloListDto> expectedList = trelloListDtoList;

        // When
        List<TrelloListDto> mapResult = trelloMapper.mapToListDto(givenList);

        // Then
        assertEquals(expectedList, mapResult);
    }

    @Test
    public void testMapToListDtoNullOrEmpty() {
        // Given & When & Then
        assertEquals(EMPTY_LIST_TRELLO_LIST_DTO, trelloMapper.mapToListDto(EMPTY_LIST_TRELLO_LIST));
        assertNull(trelloMapper.mapToListDto(null));
    }

    @Test
    public void testMapToBoards() {
        // Given
        List<TrelloBoardDto> givenList = trelloBoardDtoList;
        List<TrelloBoard> expectedList = trelloBoardList;

        // When
        List<TrelloBoard> mapResult = trelloMapper.mapToBoards(givenList);

        // Then
        assertEquals(expectedList, mapResult);
    }

    @Test
    public void testMapToBoardsNullOrEmpty() {
        // Given & When & Then
        assertEquals(EMPTY_LIST_TRELLO_BOARD, trelloMapper.mapToBoards(EMPTY_LIST_TRELLO_BOARD_DTO));
        assertNull(trelloMapper.mapToBoards(null));
    }

    @Test
    public void testMapToBoardsDto() {
        // Given
        List<TrelloBoard> givenList = trelloBoardList;
        List<TrelloBoardDto> expectedList = trelloBoardDtoList;

        // When
        List<TrelloBoardDto> mapResult = trelloMapper.mapToBoardsDto(givenList);

        // Then
        assertEquals(expectedList, mapResult);
    }

    @Test
    public void testMapToBoardsDtoNullOrEmpty() {
        // Given & When & Then
        assertEquals(EMPTY_LIST_TRELLO_BOARD_DTO, trelloMapper.mapToBoardsDto(EMPTY_LIST_TRELLO_BOARD));
        assertNull(trelloMapper.mapToBoardsDto(null));
    }

    @Test
    public void testMapToCardDto() {
        // Given
        TrelloCard givenCard1 = new TrelloCard("Name1", "Description1", "Position1", "ListId1");
        TrelloCardDto expectedCardDto1 = new TrelloCardDto("Name1", "Description1", "Position1", "ListId1");
        TrelloCard givenCard2 = new TrelloCard("", "", "", "");
        TrelloCardDto expectedCardDto2 = new TrelloCardDto("", "", "", "");
        TrelloCard givenCard3 = new TrelloCard(null, null, null, null);
        TrelloCardDto expectedCardDto3 = new TrelloCardDto(null, null, null, null);

        // When
        TrelloCardDto mapResult1 = trelloMapper.mapToCardDto(givenCard1);
        TrelloCardDto mapResult2 = trelloMapper.mapToCardDto(givenCard2);
        TrelloCardDto mapResult3 = trelloMapper.mapToCardDto(givenCard3);

        // Then
        assertEquals(expectedCardDto1, mapResult1);
        assertEquals(expectedCardDto2, mapResult2);
        assertEquals(expectedCardDto3, mapResult3);
    }

    @Test
    public void testMapToCard() {
        // Given
        TrelloCardDto givenCardDto1 = new TrelloCardDto("Name1", "Description1", "Position1", "ListId1");
        TrelloCard expectedCard1 = new TrelloCard("Name1", "Description1", "Position1", "ListId1");
        TrelloCardDto givenCardDto2 = new TrelloCardDto("", "", "", "");
        TrelloCard expectedCard2 = new TrelloCard("", "", "", "");
        TrelloCardDto givenCardDto3 = new TrelloCardDto(null, null, null, null);
        TrelloCard expectedCard3 = new TrelloCard(null, null, null, null);

        // When
        TrelloCard mapResult1 = trelloMapper.mapToCard(givenCardDto1);
        TrelloCard mapResult2 = trelloMapper.mapToCard(givenCardDto2);
        TrelloCard mapResult3 = trelloMapper.mapToCard(givenCardDto3);

        // Then
        assertEquals(expectedCard1, mapResult1);
        assertEquals(expectedCard2, mapResult2);
        assertEquals(expectedCard3, mapResult3);
    }
}
