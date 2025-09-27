package com.veri.taskmanager.service;

import com.veri.taskmanager.dto.TaskDto;
import com.veri.taskmanager.entity.Task;
import com.veri.taskmanager.entity.User;
import com.veri.taskmanager.repository.TaskRepository;
import com.veri.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task createTask(TaskDto dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setUser(user);

        return taskRepository.save(task);
    }


    public List<Task> getTasks(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUser(user);
    }

    public Task getTask(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findById(id)
                .filter(task -> task.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Task not found or not yours"));
    }

    public Task updateTask(Long id, TaskDto dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(id)
                .filter(t -> t.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Task not found or not yours"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(id)
                .filter(t -> t.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Task not found or not yours"));

        taskRepository.delete(task);
    }
}
