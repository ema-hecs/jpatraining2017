package com.hermesecs.jpatraining.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class PersistenceConfiguration {

    private static final String PERSISTENCE_UNIT_NAME = "hermesDataStore";

    @Bean
    public EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return entityManagerFactory;
    }

    @Bean
    public EntityManager createEntityManager(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }
}
