package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DBService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DBService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetTasks() throws Exception {
        // Given
        Task task = new Task(Long.MAX_VALUE, "Test Title", "Test Content");
        List<Task> tasks = Arrays.asList(task);
        TaskDto taskDto = new TaskDto(Long.MAX_VALUE, "Test Title", "Test Content");
        List<TaskDto> taskDtos = Arrays.asList(taskDto);

        when(service.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        // When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(Long.valueOf(Long.MAX_VALUE))))
                .andExpect(jsonPath("$[0].title", is("Test Title")))
                .andExpect(jsonPath("$[0].content", is("Test Content")));
    }

    @Test
    public void shouldGetTaskById() throws Exception {
        // Given
        Task task = new Task(Long.MAX_VALUE, "Test Title", "Test Content");
        TaskDto taskDto = new TaskDto(Long.MAX_VALUE, "Test Title", "Test Content");

        when(service.getTask(Long.MAX_VALUE)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(get("/v1/tasks/" + Long.MAX_VALUE).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Long.valueOf(Long.MAX_VALUE))))
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.content", is("Test Content")));
    }

    @Test
    public void shouldSendNotFoundStatusWhenGetTask() throws Exception {
        // Given
        when(service.getTask(Long.MAX_VALUE)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/v1/tasks/" + Long.MAX_VALUE).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteTaskById() throws Exception {
        // Given
        when(service.taskExists(Long.MAX_VALUE)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/v1/tasks/" + Long.MAX_VALUE).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(service, times(1)).deleteTask(Long.MAX_VALUE);
    }

    @Test
    public void shouldSendNotFoundStatusWhenDeleteTask() throws Exception {
        // Given
        when(service.taskExists(Long.MAX_VALUE)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/v1/tasks/" + Long.MAX_VALUE).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(service, times(0)).deleteTask(any());
    }

    @Test
    public void shouldDeleteAllTasks() throws Exception {
        // Given & When & Then
        mockMvc.perform(delete("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                        .header("confirm-delete-all", true))
                .andExpect(status().isNoContent());

        Mockito.verify(service, times(1)).deleteAllTasks();
    }

    @Test
    public void shouldSendBadRequestWhenDeleteAllTasks() throws Exception {
        // Given & When & Then
        mockMvc.perform(delete("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                // missing header: confirm-delete-all = true
                .andExpect(status().isBadRequest());

        Mockito.verify(service, times(0)).deleteAllTasks();
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        // Given
        Task task = new Task(Long.MAX_VALUE, "Test Title", "Test Content");
        TaskDto taskDto = new TaskDto(Long.MAX_VALUE, "Test Title", "Test Content");

        when(service.taskExists(Long.MAX_VALUE)).thenReturn(true);
        when(service.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(Long.valueOf(Long.MAX_VALUE))))
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.content", is("Test Content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        // Given
        Task newTask = new Task(null, "Test Title", "Test Content");
        TaskDto newTaskDto = new TaskDto(null, "Test Title", "Test Content");
        Task createdTask = new Task(Long.MAX_VALUE, "Test Title", "Test Content");
        TaskDto createdTaskDto = new TaskDto(Long.MAX_VALUE, "Test Title", "Test Content");

        when(service.saveTask(newTask)).thenReturn(createdTask);
        when(taskMapper.mapToTask(newTaskDto)).thenReturn(newTask);
        when(taskMapper.mapToTaskDto(createdTask)).thenReturn(createdTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(newTaskDto);

        // When & Then
        mockMvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(Long.valueOf(Long.MAX_VALUE))))
                .andExpect(jsonPath("$.title", is("Test Title")))
                .andExpect(jsonPath("$.content", is("Test Content")));
    }
}