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
    public void createTask(String title, String description, LocalDateTime dueDate, Long assignedToId, Long createdById, List<String> tags) {

        Task task = createTaskFromDetails(title, description, dueDate, assignedToId, createdById);
        new TaskRepository().createTask(task);
        Task lastInsertedTask = new TaskRepository().getLastInsertedTask();
        for (String tag : tags) {
            new TagTaskService().createTagTask(Long.parseLong(tag), lastInsertedTask.getId());
        }
        new TokenService().createToken(assignedToId);
    }

    public void updateTask() {
        // Update a task
    }

    public void deleteTask() {
        // Delete a task
    }

    public List<Task> displayTasks() {
        return new TaskRepository().displayTasks();
    }

    public Task createTaskFromDetails(String title, String description, LocalDateTime dueDate, Long assignedToId, Long createdById) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCreationDate(new Date());
        task.setDueDate(dueDate);
        task.setAssignedTo(new User(assignedToId));
        task.setCreatedBy(new User(createdById));
        return task;
    }
}
