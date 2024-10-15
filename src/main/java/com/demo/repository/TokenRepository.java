package com.demo.repository;

import com.demo.entity.Token;
import com.demo.entity.User;
import com.demo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.List;

public class TokenRepository {
    public void createToken(Token token) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(token);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public Token findActiveTokenByUserAndType(User user, String tokenType) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Token t WHERE t.user = :user AND t.type = :type AND t.status = 'active'", Token.class)
                    .setParameter("user", user)
                    .setParameter("type", tokenType)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void updateToken(Token token) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(token);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to update token", e);
        } finally {
            em.close();
        }
    }
    public List<Token> getTokensForUnansweredRequests() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            LocalDateTime twelveHoursAgo = LocalDateTime.now().minusHours(12);
            return em.createQuery("SELECT t FROM Token t WHERE t.type = 'modification' AND t.creationDate < :twelveHoursAgo AND t.status = 'pending'", Token.class)
                    .setParameter("twelveHoursAgo", twelveHoursAgo)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}