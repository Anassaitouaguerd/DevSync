package com.demo.repository;

import com.demo.entity.Tag;
import com.demo.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TagRepository {
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
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to display tags", e);
        } finally {
            em.close();
        }
    }
}
