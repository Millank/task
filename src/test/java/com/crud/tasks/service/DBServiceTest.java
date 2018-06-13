package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DBServiceTest {
    @InjectMocks
    private DBService service;

    @Mock
    TaskRepository repository;

    @Test
    public void getAllTasks() {
        // Given
        List<Task> tasks = Arrays.asList(new Task(1L, "test_title", "test_content"));
        when(repository.findAll()).thenReturn(tasks);

        // When
        List<Task> result = service.getAllTasks();

        // Then
        assertEquals(tasks, result);
    }

    @Test
    public void getTask() {
        // Given
        Task task = new Task(1L, "test_title", "test_content");
        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(repository.findById(2L)).thenReturn(Optional.empty());

        // When
        Optional<Task> result1 = service.getTask(1L);
        Optional<Task> result2 = service.getTask(2L);

        // Then
        assertTrue(result1.isPresent());
        assertEquals(task, result1.get());
        assertFalse(result2.isPresent());
    }

    @Test
    public void saveTask() {
        // Given
        Task task = new Task(2L, "test_title", "test_content");
        when(repository.save(task)).thenReturn(task);

        // When
        Task result = service.saveTask(task);

        //Then
        assertEquals(task, result);
    }

    @Test
    public void taskExists() {
        // Given
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.existsById(2L)).thenReturn(false);

        // When and Then
        assertTrue(service.taskExists(1L));
        assertFalse(service.taskExists(2L));
    }

    @Test
    public void deleteTask() {
        // Given and When
        service.deleteTask(1L);

        // Then
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteAllTasks() {
        // Given and When
        service.deleteAllTasks();

        // Then
        verify(repository, times(1)).deleteAll();
    }
}