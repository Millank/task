package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloMapper {
    public List<TrelloBoard> mapToBoards(List<TrelloBoardDto>  trelloBoardDtoList) {
        if (trelloBoardDtoList == null) return null;
        return trelloBoardDtoList.stream()
                .map(trelloBoardDto -> trelloBoardDto == null ? null :
                    new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(),
                                    mapToList(trelloBoardDto.getLists())))
                .collect(toList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(List<TrelloBoard>  trelloBoardList) {
        if (trelloBoardList == null) return null;
        return trelloBoardList.stream()
                .map(trelloBoard -> trelloBoard == null ? null :
                    new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(),
                                       mapToListDto(trelloBoard.getLists())))
                .collect(toList());
    }

    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListDtoList) {
        if (trelloListDtoList == null) return null;
        return trelloListDtoList.stream()
                .map(listDto -> listDto == null ? null :
                    new TrelloList(listDto.getId(), listDto.getName(), listDto.isClosed()))
                .collect(toList());
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloLists) {
        if (trelloLists == null) return null;
        return trelloLists.stream()
                .map(list -> list == null ? null : new TrelloListDto(list.getId(), list.getName(), list.isClosed()))
                .collect(toList());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        if (trelloCard == null) return null;
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPos(),
                                 trelloCard.getListId());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        if (trelloCardDto == null) return null;
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(), trelloCardDto.getPos(),
                              trelloCardDto.getListId());
    }
}
