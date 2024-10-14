package com.demo.interfaces;

import com.demo.entity.Task;
import com.demo.entity.User;

import java.util.List;

public interface TaskInterface {
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long taskId);
    List<Task> displayTasks(User user);
    Task getLastInsertedTask();
    Task getTaskById(Long taskId);
    List<Task> getOverdueTasks();
    List<Task> getTasksByAssignedUser(User user);
}