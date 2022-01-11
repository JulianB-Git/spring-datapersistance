package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return convertPetToPetDTO(petService.savePet(convertPetDTOToPet(petDTO)));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return convertToPetsDTOList(petService.getAllPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return convertToPetsDTOList(petService.getPetsByOwnerId(ownerId));
    }

    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet, "ownerId");
        Customer customer = userService.getCustomerById(petDTO.getOwnerId());
        pet.setOwner(customer);
        pet.setPetType(petDTO.getType());
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO,"owner");
        Customer customer = userService.getCustomerById(pet.getOwner().getId());
        petDTO.setOwnerId(customer.getId());
        petDTO.setType(pet.getPetType());
        return petDTO;
    }

    private List<PetDTO> convertToPetsDTOList(List<Pet> pets){
        List<PetDTO> petsDTO = new ArrayList<>();

        for (Pet pet: pets) {
            petsDTO.add(convertPetToPetDTO(pet));
        }

        return petsDTO;
    }
}
