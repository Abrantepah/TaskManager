package com.veri.taskmanager.controller;

import com.veri.taskmanager.dto.TaskDto;
import com.veri.taskmanager.entity.Task;
import com.veri.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        logger.info("User {} is creating a task: {}", username, taskDto.getTitle());
        Task created = taskService.createTask(taskDto, username);
        logger.info("Task created successfully for {}: taskId={}", username, created.getId());
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Authentication authentication) {
        String username = authentication.getName();
        logger.info("Fetching all tasks for user {}", username);
        return ResponseEntity.ok(taskService.getTasks(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        logger.info("User {} is fetching task with id {}", username, id);
        return ResponseEntity.ok(taskService.getTask(id, username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        logger.info("User {} is updating task id {} with new data: {}", username, id, taskDto);
        return ResponseEntity.ok(taskService.updateTask(id, taskDto, username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        logger.warn("User {} is deleting task with id {}", username, id);
        taskService.deleteTask(id, username);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Task deleted");
        return ResponseEntity.ok(response);
    }
}
