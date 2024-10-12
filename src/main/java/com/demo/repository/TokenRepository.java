package com.demo.repository;

import com.demo.entity.Token;
import com.demo.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class TokenRepository {
    public void createToken(Token token) {
        // Create a token
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
}
