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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        logger.info("User {} is creating a task: {}", username, taskDto.getTitle());

        Task created = taskService.createTask(taskDto, username);
        logger.info("Task created successfully for {}: taskId={}", username, created.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Task created");
        response.put("task", created);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getTasks(Authentication authentication) {
        String username = authentication.getName();
        logger.info("Fetching all tasks for user {}", username);

        List<Task> tasks = taskService.getTasks(username);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("tasks", tasks);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTask(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        logger.info("User {} is fetching task with id {}", username, id);

        Task task = taskService.getTask(id, username);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("task", task);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto, Authentication authentication) {
        String username = authentication.getName();
        logger.info("User {} is updating task id {} with new data: {}", username, id, taskDto);

        Task updated = taskService.updateTask(id, taskDto, username);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Task updated");
        response.put("task", updated);

        return ResponseEntity.ok(response);
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
