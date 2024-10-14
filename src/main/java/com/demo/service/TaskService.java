package com.demo.service;

import com.demo.entity.Task;
import com.demo.entity.User;
import com.demo.repository.TaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class TaskService {
    private TaskRepository taskRepository;
    private TokenService tokenService;

    public TaskService() {
        this.taskRepository = new TaskRepository();
        this.tokenService = new TokenService();
    }

    public void createTask(String title, String description, LocalDateTime dueDate, Long assignedToId, Long createdById, List<String> tags) {
        LocalDateTime now = LocalDateTime.now();
        if (dueDate.isBefore(now)) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
        LocalDateTime maxDueDate = now.plusDays(3);
        if (dueDate.isAfter(maxDueDate)) {
            throw new IllegalArgumentException("Due date cannot be more than 3 days in the future");
        }
        if (tags.size() < 2) {
            throw new IllegalArgumentException("At least two tags are required");
        }

        Task task = createTaskFromDetails(title, description, dueDate, assignedToId, createdById);
        taskRepository.createTask(task);
        Task lastInsertedTask = taskRepository.getLastInsertedTask();
        for (String tag : tags) {
            new TagTaskService().createTagTask(Long.parseLong(tag), lastInsertedTask.getId());
        }
        tokenService.createToken(assignedToId);
    }

    public void updateTask(Long taskId, String title, String description, LocalDateTime dueDate, Long assignedToId, Long userId, List<String> tags) {
        Task taskUPD = taskRepository.getTaskById(taskId);
        if (taskUPD.getCreatedBy().getId().equals(userId)) {
            // Delete without using a token
        }
        else {
            if(!tokenService.useToken(userId, "remplacer")) {
                throw new IllegalStateException("No replacement tokens available");
            }
        }
        Task task = createTaskFromDetails(title, description, dueDate, assignedToId, userId);
        task.setId(taskId);
        taskRepository.updateTask(task);
        new TagTaskService().deleteTagTask(task.getId());
        for (String tag : tags) {
            new TagTaskService().createTagTask(Long.parseLong(tag), task.getId());
        }
    }

    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.getTaskById(taskId);
        if (task.getCreatedBy().getId().equals(userId)) {
            // Delete without using a token
        } else {
            if (!tokenService.useToken(userId, "suppression")) {
                throw new IllegalStateException("No deletion tokens available");
            }
        }
        // Existing delete logic
        taskRepository.deleteTask(taskId);
    }

    public List<Task> displayTasks(User user) {
        return taskRepository.displayTasks(user);
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.getTaskById(taskId);
    }

    public Task createTaskFromDetails(String title, String description, LocalDateTime dueDate, Long assignedToId, Long createdById) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCreationDate(LocalDateTime.now());
        task.setDueDate(dueDate);
        task.setAssignedTo(new User(assignedToId));
        task.setCreatedBy(new User(createdById));
        return task;
    }

    public void markTaskAsCompleted(Long taskId) {
        Task task = taskRepository.getTaskById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task not found");
        }
        if (LocalDateTime.now().isAfter(task.getDueDate())) {
            throw new IllegalArgumentException("Cannot mark task as completed after the due date");
        }
        task.setCompleted(true);
        taskRepository.updateTask(task);
    }
}