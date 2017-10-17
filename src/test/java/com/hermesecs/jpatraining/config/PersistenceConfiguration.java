package com.hermesecs.jpatraining.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ComponentScan(basePackages = {"com.hermesecs.jpatraining.repository"})
public class PersistenceConfiguration {

    private static final String PERSISTENCE_UNIT_NAME = "hermesDataStoreTest";

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

    @Bean("foo")
    public PlatformTransactionManager createTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
