package com.demo.repository;

import com.demo.entity.Task;
import com.demo.interfaces.TaskInterface;
import com.demo.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TaskRepository implements TaskInterface {
    public void createTask(Task task) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to create task", e);
        } finally {
            em.close();
        }
    }

    public void updateTask() {
        // Update a task
    }

    public void deleteTask() {
        // Delete a task
    }

    public List<Task> displayTasks() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to display tasks", e);
        }
    }
    public Task getLastInsertedTask() {
    try (EntityManager em = JPAUtil.getEntityManager()) {
        return em.createQuery("SELECT t FROM Task t ORDER BY t.id DESC", Task.class)
                 .setMaxResults(1)
                 .getSingleResult();
    } catch (Exception e) {
        throw new RuntimeException("Failed to get last inserted task", e);
    }
}
}
