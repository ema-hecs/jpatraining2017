package com.hermesecs.jpatraining.repository.jpa;

import com.hermesecs.jpatraining.domain.capability.Identifiable;
import com.hermesecs.jpatraining.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Transactional
abstract class JpaRepository<E extends Identifiable<I>, I> implements Repository<E, I> {

    private static final String ENTITY_MANAGER_NOT_NULL = "entityManager must not be null.";
    private static final String ENTITY_CLASS_NOT_NULL = "entityClass must not be null.";
    private static final String ENTITY_NOT_NULL = "entity must not be null.";
    private static final String ID_NOT_NULL = "id must not be null.";

    private EntityManager entityManager;

    private Class<E> entityClass;

    public JpaRepository(EntityManager entityManager, Class<E> entityClass) {
        requireNonNull(entityManager, ENTITY_MANAGER_NOT_NULL);
        requireNonNull(entityClass, ENTITY_CLASS_NOT_NULL);

        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    public long count() {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(entityClass)));
        return entityManager
                .createQuery(cq)
                .getSingleResult();
    }

    @Override
    public E findOne(I id) {
        requireNonNull(id, ID_NOT_NULL);

        return entityManager.find(entityClass, id);
    }

    @Override
    public List<E> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> cq = cb.createQuery(entityClass);
        Root<E> rootEntry = cq.from(entityClass);
        return entityManager
                .createQuery(cq.select(rootEntry))
                .getResultList();
    }

    @Override
    public E save(E entity) {
        requireNonNull(entity, ENTITY_NOT_NULL);

        if (!hasId(entity)) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public void delete(E entity) {
        requireNonNull(entity, ENTITY_NOT_NULL);

        entityManager.remove(entity);
    }

    protected Class<E> getEntityClass() {
        return entityClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    private boolean hasId(final E entity) {
        return entity.getId() != null;
    }
}
