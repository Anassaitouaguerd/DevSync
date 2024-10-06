package com.demo.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final String PERSISTENCE_UNIT_NAME = "DevSyncPU";
    private static EntityManagerFactory factory;
    private static EntityManager em;

    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        em = factory.createEntityManager();
        return em;
    }

    public static void shutdown() {
        if (factory != null) {
            em.close();
        }
    }
}