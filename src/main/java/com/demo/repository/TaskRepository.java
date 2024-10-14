package com.demo.repository;

import com.demo.entity.Task;
import com.demo.entity.User;
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

    public void updateTask(Task task) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(task);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to update task", e);
        } finally {
            em.close();
        }
    }

    public void deleteTask(Long taskId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Task task = em.find(Task.class, taskId);
            if (task != null) {
                em.remove(task);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to delete task", e);
        } finally {
            em.close();
        }
    }

    public List<Task> displayTasks(User user) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT t FROM Task t WHERE t.assignedTo.id = :userId OR t.createdBy.id = :userId", Task.class)
                    .setParameter("userId", user.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to display tasks", e);
        }
    }

    public Task getLastInsertedTask() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT t FROM Task t ORDER BY t.id DESC", Task.class)
                    .setMaxResults(1)
                    .getSingleResult();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get last inserted task", e);
        }
    }

    public Task getTaskById(Long taskId) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT t FROM Task t LEFT JOIN FETCH t.tags WHERE t.id = :id", Task.class)
                    .setParameter("id", taskId)
                    .getSingleResult();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get task by id", e);
        }
    }

    public List<Task> getOverdueTasks() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT t FROM Task t WHERE t.dueDate < CURRENT_TIMESTAMP AND t.completed = false", Task.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get overdue tasks", e);
        }
    }

    public List<Task> getTasksByAssignedUser(User user) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT t FROM Task t WHERE t.assignedTo = :user", Task.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get tasks by assigned user", e);
        }
    }
}