package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloDto {
    @JsonProperty("board")
    private Integer board;

    @JsonProperty("card")
    private Integer card;
}
