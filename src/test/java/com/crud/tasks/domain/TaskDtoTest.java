package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskDtoTest {
    @Test
    public void testGettersAfterNoArgsConstructor() {
        // Given
        TaskDto taskDto = new TaskDto();

        // When and Then
        assertNull(taskDto.getId());
        assertNull(taskDto.getTitle());
        assertNull(taskDto.getContent());
    }

    @Test
    public void testGettersAfterAllArgsConstructor() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");

        // When and Then
        assertEquals(Long.valueOf(1), taskDto.getId());
        assertEquals("test_title", taskDto.getTitle());
        assertEquals("test_content", taskDto.getContent());
    }

    @Test
    public void testEqualsAfterNoArgsConstructor() {
        // Given
        TaskDto taskDto1 = new TaskDto();
        TaskDto taskDto2 = new TaskDto();
        TaskDto taskDtoDiff1 = new TaskDto(1L, null, null);
        TaskDto taskDtoDiff2 = new TaskDto(null, "test_title", null);
        TaskDto taskDtoDiff3 = new TaskDto(null, null, "test_content");

        // When and Then
        assertTrue(taskDto1.equals(taskDto1));
        assertTrue(taskDto1.equals(taskDto2));
        assertFalse(taskDto1.equals(taskDtoDiff1));
        assertFalse(taskDto1.equals(taskDtoDiff2));
        assertFalse(taskDto1.equals(taskDtoDiff3));
        assertFalse(taskDto1.equals(null));
    }

    @Test
    public void testEqualsAfterAllArgsConstructor() {
        // Given
        TaskDto taskDto1 = new TaskDto(1L, "test_title", "test_content");
        TaskDto taskDto2 = new TaskDto(1L, "test_title", "test_content");
        TaskDto taskDtoDiff1 = new TaskDto(2L, "test_title", "test_content");
        TaskDto taskDtoDiff2 = new TaskDto(1L, "test_title_diff", "test_content");
        TaskDto taskDtoDiff3 = new TaskDto(1L, "test_title", "test_content_diff");
        TaskDto taskDtoDiff4 = new TaskDto();

        // When and Then
        assertTrue(taskDto1.equals(taskDto1));
        assertTrue(taskDto1.equals(taskDto2));
        assertFalse(taskDto1.equals(taskDtoDiff1));
        assertFalse(taskDto1.equals(taskDtoDiff2));
        assertFalse(taskDto1.equals(taskDtoDiff3));
        assertFalse(taskDto1.equals(taskDtoDiff4));
        assertFalse(taskDto1.equals(null));
    }

    @Test
    public void testHashCode() {
        // Given
        TaskDto taskDto1a = new TaskDto();
        TaskDto taskDto1b = new TaskDto();
        TaskDto taskDto2a = new TaskDto(1L, null, null);
        TaskDto taskDto2b = new TaskDto(1L, null, null);
        TaskDto taskDto3a = new TaskDto(null, "test_title", null);
        TaskDto taskDto3b = new TaskDto(null, "test_title", null);
        TaskDto taskDto4a = new TaskDto(null, null, "test_content");
        TaskDto taskDto4b = new TaskDto(null, null, "test_content");
        TaskDto taskDto5a = new TaskDto(null, "test_title", "test_content");
        TaskDto taskDto5b = new TaskDto(null, "test_title", "test_content");
        TaskDto taskDto6a = new TaskDto(1L, null, "test_content");
        TaskDto taskDto6b = new TaskDto(1L, null, "test_content");
        TaskDto taskDto7a = new TaskDto(1L, "test_title", null);
        TaskDto taskDto7b = new TaskDto(1L, "test_title", null);
        TaskDto taskDto8a = new TaskDto(1L, "test_title", "test_content");
        TaskDto taskDto8b = new TaskDto(1L, "test_title", "test_content");

        // When and Then
        assertTrue(taskDto1a.hashCode() == taskDto1b.hashCode());
        assertTrue(taskDto2a.hashCode() == taskDto2b.hashCode());
        assertTrue(taskDto3a.hashCode() == taskDto3b.hashCode());
        assertTrue(taskDto4a.hashCode() == taskDto4b.hashCode());
        assertTrue(taskDto5a.hashCode() == taskDto5b.hashCode());
        assertTrue(taskDto6a.hashCode() == taskDto6b.hashCode());
        assertTrue(taskDto7a.hashCode() == taskDto7b.hashCode());
        assertTrue(taskDto8a.hashCode() == taskDto8b.hashCode());
    }

    @Test
    public void testToString() {
        // Given
        TaskDto taskDto1 = new TaskDto(1L, "test_title", "test_content");
        TaskDto taskDto2 = new TaskDto();

        // When
        String result1 = taskDto1.toString();
        String result2 = taskDto2.toString();

        // Then
        System.out.println(result1);
        assertTrue(result1.matches("\\W*TaskDto\\W+id\\W+1\\W+title\\W+test_title\\W+content\\W+test_content\\W*"));
        System.out.println(result2);
        assertTrue(result2.matches("\\W*TaskDto\\W+id\\W+null\\W+title\\W+null\\W+content\\W+null\\W*"));
    }
}