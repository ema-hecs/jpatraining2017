package com.hermesecs.jpatraining.repository.jpa;

import com.hermesecs.jpatraining.config.PersistenceConfiguration;
import com.hermesecs.jpatraining.domain.Collaborator;
import com.hermesecs.jpatraining.domain.QualificationLevel;
import com.hermesecs.jpatraining.repository.CollaboratorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class CollaboratorRepositoryJpaImplTest {

    private final static Long ZERO = 0L;

    @PersistenceContext
    private EntityManager entityManager;

    private CollaboratorRepository collaboratorRepository;

    @Before
    public void setUp() {
        collaboratorRepository = new CollaboratorRepositoryJpaImpl(entityManager);
    }

    @Test
    public void testFindAllWhenNoCollaborators() {
        //WHEN
        List<Collaborator> collaborators = collaboratorRepository.findAll();

        //THEN
        assertThat(collaborators, is(empty()));
    }

    @Test
    public void testCountWhenNoCollaborators() {
        //WHEN
        Long count = collaboratorRepository.count();

        //THEN
        assertEquals(count, ZERO);
    }

    @Test
    public void testFindAllWithTwoCollaborators() {
        //GIVEN
        Collaborator collaborator1 = new Collaborator("Etienne", "Martel");
        Collaborator collaborator2 = new Collaborator("Enneite", "Letram");
        collaboratorRepository.save(collaborator1);
        collaboratorRepository.save(collaborator2);
        flushAndClear();

        //WHEN
        List<Collaborator> collaborators = collaboratorRepository.findAll();

        //THEN
        assertThat(collaborators, hasSize(2));
        assertThat(collaborators, contains(collaborator1, collaborator2));
    }

    @Test
    public void testFindOne() {
        //GIVEN
        Collaborator collaborator = new Collaborator("Etienne", "Martel");
        collaboratorRepository.save(collaborator);
        //entityManager.flush();
        Long id = collaborator.getId();
        flushAndClear();

        //WHEN
        Collaborator collaboratorFound = collaboratorRepository.findOne(id);

        //THEN
        assertEquals(collaborator, collaboratorFound);
    }

    @Test
    public void testFindAllActive() {
        //GIVEN
        Collaborator collaboratorActive = new Collaborator("Etienne", "Martel");
        Collaborator collaboratorInactive = new Collaborator("Enneite", "Letram");
        collaboratorInactive.setLeaveDate(LocalDate.now());
        collaboratorRepository.save(collaboratorActive);
        collaboratorRepository.save(collaboratorInactive);
        flushAndClear();

        //WHEN
        List<Collaborator> collaborators = collaboratorRepository.findAllActive();

        //THEN
        assertThat(collaborators, hasSize(1));
        assertThat(collaborators, contains(collaboratorActive));
    }

    @Test
    public void testFindAllActiveForInactiveTomorrow() {
        //GIVEN
        Collaborator collaboratorInactiveTomorrow = new Collaborator("Etienne", "Martel");
        collaboratorInactiveTomorrow.setHiringDate(LocalDate.now().minus(Period.ofMonths(1)));
        collaboratorInactiveTomorrow.setLeaveDate(LocalDate.now().plus(Period.ofDays(1)));
        collaboratorRepository.save(collaboratorInactiveTomorrow);
        flushAndClear();

        //WHEN
        List<Collaborator> collaborators = collaboratorRepository.findAllActive();

        //THEN
        assertThat(collaborators, hasSize(1));
        assertThat(collaborators, contains(collaboratorInactiveTomorrow));
    }

    @Test
    public void testFindAllWithSameLevel() {
        //GIVEN
        Collaborator collaboratorMedior = new Collaborator("Etienne", "Martel");
        collaboratorMedior.setQualificationLevel(QualificationLevel.MEDIOR);
        Collaborator collaboratorSenior = new Collaborator("Gaël", "T.");
        collaboratorSenior.setQualificationLevel(QualificationLevel.SENIOR);
        collaboratorRepository.save(collaboratorMedior);
        collaboratorRepository.save(collaboratorSenior);
        flushAndClear();

        //WHEN
        List<Collaborator> collaborators = collaboratorRepository.findAllWithSameLevel(QualificationLevel.MEDIOR);

        //THEN
        assertThat(collaborators, hasSize(1));
        assertThat(collaborators, contains(collaboratorMedior));
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
