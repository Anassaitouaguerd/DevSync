package com.demo.interfaces;

import com.demo.entity.Task;

import java.util.List;

public interface TaskInterface {
    void createTask(Task task);
    void updateTask();
    void deleteTask();
    List<Task> displayTasks();
}
