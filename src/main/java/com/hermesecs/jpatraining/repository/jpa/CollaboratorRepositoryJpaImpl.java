package com.hermesecs.jpatraining.repository.jpa;

import com.hermesecs.jpatraining.domain.Collaborator;
import com.hermesecs.jpatraining.domain.QualificationLevel;
import com.hermesecs.jpatraining.repository.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Repository
@Transactional
class CollaboratorRepositoryJpaImpl extends JpaRepository<Collaborator, Long> implements CollaboratorRepository {

    private static final String QUALIFICATION_LEVEL_NOT_NULL = "qualitificationLevel must not be null.";

    public CollaboratorRepositoryJpaImpl(@Autowired EntityManager entityManager) {
        super(entityManager, Collaborator.class);
    }

    @Override
    public List<Collaborator> findAllWithSameLevel(QualificationLevel qualificationLevel) { //TODO add tests
        requireNonNull(qualificationLevel, QUALIFICATION_LEVEL_NOT_NULL);

        return getEntityManager()
                .createQuery("select c from Collaborator c where c.qualificationLevel = :level", Collaborator.class)
                .setParameter("level", qualificationLevel)
                .getResultList();
    }

    @Override
    public List<Collaborator> findAllActive() {
        return getEntityManager()
                .createQuery("select c from Collaborator c where c.leaveDate is null or c.leaveDate > CURRENT_DATE", Collaborator.class)
                .getResultList();
    }


}
