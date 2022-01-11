package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    String name;

    private String phoneNumber;

    @Column(length = 500)
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Pet> pets;

    public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> pets) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void addPet(Pet pet){
        if (pets == null) {
            pets = new ArrayList<>();
        }

        pets.add(pet);
    }
}
