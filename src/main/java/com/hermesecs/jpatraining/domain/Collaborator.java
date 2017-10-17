package com.hermesecs.jpatraining.domain;

import com.hermesecs.jpatraining.domain.capability.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;

@Entity
public class Collaborator implements Identifiable<Long> {

    @Id
    @SequenceGenerator(name = "collaboratorSeqGen", sequenceName = "COLLABORATOR_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collaboratorSeqGen")
    private Long id;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    private long salary;

    @Column(nullable = false)
    private LocalDate hiringDate;

    private LocalDate leaveDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QualificationLevel qualificationLevel;

    protected Collaborator() { /* JPA purpose */ }

    public Collaborator(String firstName, String lastName) {
        this(firstName, lastName, LocalDate.now(), QualificationLevel.JUNIOR);
    }

    public Collaborator(String firstName, String lastName, LocalDate hiringDate, QualificationLevel qualificationLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hiringDate = hiringDate;
        this.qualificationLevel = qualificationLevel;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }

    public QualificationLevel getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(QualificationLevel qualificationLevel) {
        this.qualificationLevel = qualificationLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Collaborator collaborator = (Collaborator) o;

        if (salary != collaborator.salary) return false;
        if (id != null ? !id.equals(collaborator.id) : collaborator.id != null) return false;
        if (!lastName.equals(collaborator.lastName)) return false;
        if (!firstName.equals(collaborator.firstName)) return false;
        if (!hiringDate.equals(collaborator.hiringDate)) return false;
        if (leaveDate != null ? !leaveDate.equals(collaborator.leaveDate) : collaborator.leaveDate != null) return false;
        return qualificationLevel == collaborator.qualificationLevel;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + (int) (salary ^ (salary >>> 32));
        result = 31 * result + hiringDate.hashCode();
        result = 31 * result + (leaveDate != null ? leaveDate.hashCode() : 0);
        result = 31 * result + qualificationLevel.hashCode();
        return result;
    }

}
