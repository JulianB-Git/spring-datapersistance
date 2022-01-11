package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exceptions.PetNotFoundException;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        Customer customer = savedPet.getOwner();

        customer.addPet(savedPet);
        customerRepository.save(customer);

        return savedPet;
    }

    public List<Pet> getPetsByOwnerId(Long id) {
        Customer customer = customerRepository.getOne(id);

        return customer.getPets();
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        Pet pet = optionalPet.orElseThrow(PetNotFoundException::new);

        return pet;
    }

}
