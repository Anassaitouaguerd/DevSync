package com.demo.repository;

import com.demo.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class TagTaskRepository {
    public void createTagTask(Long tagId, Long taskId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("INSERT INTO task_tags (task_id, tag_id) VALUES (?, ?)")
              .setParameter(1, taskId)
              .setParameter(2, tagId)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to create tag-task", e);
        } finally {
            em.close();
        }
    }
}
