package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskMapperTestSuite {
    private TaskMapper taskMapper;

    @Before
    public void init() {
        taskMapper = new TaskMapper();
    }

    @Test
    public void testMapToTask() {
        // Given
        TaskDto givenTaskDto = new TaskDto(1L, "Title 1", "Content 1");
        Task expectedTask = new Task(1L, "Title 1", "Content 1");

        // When
        Task mapResult = taskMapper.mapToTask(givenTaskDto);

        // Then
        assertEquals(expectedTask, mapResult);
    }

    @Test
    public void testMapToTaskDto() {
        // Given
        Task givenTask = new Task(1L, "Title 1", "Content 1");
        TaskDto expectedTaskDto = new TaskDto(1L, "Title 1", "Content 1");

        // When
        TaskDto mapResult = taskMapper.mapToTaskDto(givenTask);

        // Then
        assertEquals(expectedTaskDto, mapResult);
    }

    @Test
    public void testMapToTaskDtoList() {
        // Given
        List<Task> givenTaskList = new ArrayList<>();
        List<TaskDto> expectedTaskDtoList = new ArrayList<>();
        givenTaskList.add(new Task(1L, "Title 1", "Content 1"));
        expectedTaskDtoList.add(new TaskDto(1L, "Title 1", "Content 1"));
        givenTaskList.add(new Task(2L, "Title 2", "Content 2"));
        expectedTaskDtoList.add(new TaskDto(2L, "Title 2", "Content 2"));
        givenTaskList.add(new Task(3L, "Title 3", "Content 3"));
        expectedTaskDtoList.add(new TaskDto(3L, "Title 3", "Content 3"));

        // When
        List<TaskDto> mapResult = taskMapper.mapToTaskDtoList(givenTaskList);

        // Then
        assertEquals(expectedTaskDtoList, mapResult);
    }
}
