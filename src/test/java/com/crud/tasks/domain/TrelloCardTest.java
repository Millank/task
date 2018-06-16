package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrelloCardTest {
    @Test
    public void testToString() {
        // Given
        TrelloCard card = new TrelloCard("test_name", "test_desc", "TEST POS", "test List Id");

        // When
        String result1 = card.toString();

        // Then
        System.out.println(result1);
        assertTrue(result1.matches("\\W*TrelloCard\\W+name\\W+test_name\\W+description\\W+test_desc\\W+" +
                                   "pos\\W+TEST POS\\W+listId\\W+test List Id\\W*"));
    }
}