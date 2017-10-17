package com.hermesecs.jpatraining.repository;

import com.hermesecs.jpatraining.domain.capability.Identifiable;

import java.util.List;

public interface Repository<E extends Identifiable<I>, I> {

    long count();

    E findOne(I id);

    List<E> findAll();

    E save(E entity);

    void delete(E entity);
}
