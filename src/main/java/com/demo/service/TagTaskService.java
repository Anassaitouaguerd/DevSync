package com.demo.service;

import com.demo.repository.TagTaskRepository;

public class TagTaskService {
    public void createTagTask(Long tagId, Long taskId) {
        new TagTaskRepository().createTagTask(tagId, taskId);
    }
    public void deleteTagTask(Long taskId) {
        // Delete a tag-task
        new TagTaskRepository().deleteTagTask(taskId);
    }
}
