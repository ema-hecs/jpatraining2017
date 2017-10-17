package com.hermesecs.jpatraining.repository;

import com.hermesecs.jpatraining.domain.Collaborator;
import com.hermesecs.jpatraining.domain.QualificationLevel;

import java.util.List;

public interface CollaboratorRepository extends Repository<Collaborator, Long> {
    List<Collaborator> findAllWithSameLevel(QualificationLevel qualificationLevel);

    List<Collaborator> findAllActive();
}
