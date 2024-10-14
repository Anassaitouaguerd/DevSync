package com.demo.repository;

import com.demo.entity.User;
import com.demo.interfaces.UserInterface;
import com.demo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements UserInterface {

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());

    @Override
    public void create(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error creating user: {0}", e.getMessage());
            throw new RuntimeException("Failed to create user", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void update(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error updating user", e);
            throw new RuntimeException("Failed to update user", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> display() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching users: {0}", e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            User findUser = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (findUser != null) {
                em.remove(findUser);
                transaction.commit();
            } else {
                LOGGER.log(Level.WARNING, "User with email {0} not found", email);
                throw new RuntimeException("User not found for email: " + email);
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error deleting user: {0}", e.getMessage());
            throw new RuntimeException("Failed to delete user", e);
        } finally {
            em.close();
        }
    }
    public Optional<User> findUserById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(User.class, id));
        } finally {
            em.close();
        }
    }

    public User find(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching user: {0}", e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }


    @Override
public Map<String, Object> findUserByEmail(String email) {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        if (user != null) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("name", user.getName());
            userInfo.put("email", user.getEmail());
            userInfo.put("password", user.getPassword());
            userInfo.put("adresse", user.getAdresse());
            userInfo.put("isManager", user.getManager());
            return userInfo;
        }
        return null;
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error fetching user: {0}", e.getMessage());
        return null;
    } finally {
        em.close();
    }
}
}
