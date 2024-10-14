package com.demo.service;

import com.demo.entity.Tag;
import com.demo.repository.TagRepository;

import java.util.List;

public class TagService {
    public void createTag() {
        // Create a tag
    }

    public void updateTag() {
        // Update a tag
    }

    public void deleteTag() {
        // Delete a tag
    }

    public List<Tag> displayTags() {
        return new TagRepository().displayTags();
    }
}
