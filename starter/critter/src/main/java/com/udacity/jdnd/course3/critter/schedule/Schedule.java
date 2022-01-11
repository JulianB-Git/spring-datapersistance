package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employeeIds;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> petIds;

    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "activities")
    private Set<EmployeeSkill> activities;

    public Schedule(Long id, List<Employee> employeeIds, List<Pet> petIds, LocalDate date, Set<EmployeeSkill> activities) {
        this.id = id;
        this.employeeIds = employeeIds;
        this.petIds = petIds;
        this.date = date;
        this.activities = activities;
    }

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Employee> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Pet> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pet> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
